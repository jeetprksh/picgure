package com.picgure.api.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picgure.api.manager.FileService;
import com.picgure.api.manager.HttpClientService;
import com.picgure.api.manager.ObjectService;
import com.picgure.api.util.Constants;
import com.picgure.api.util.SaveStatus;
import com.picgure.api.util.TranslateObjects;
import com.picgure.api.util.UrlUtil;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;
import com.picgure.entity.ImgurSubredditObjectsResponse;
import com.picgure.logging.PicgureLogger;
import com.picgure.persistence.dao.ImgurObjectDao;
import com.picgure.persistence.dao.impl.ImgurObjectDaoImpl;
import com.picgure.persistence.dto.ImgurObjectDTO;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ObjectServiceImpl implements ObjectService {
	
	private Logger logger = PicgureLogger.getLogger(ObjectServiceImpl.class);

	private HttpClientService httpClientService;
	private FileService fileService;
	private ImgurObjectDao repository;

	public ObjectServiceImpl() {
        this.httpClientService = new HttpClientServiceImpl();
        this.fileService = new FileServiceImpl();
        this.repository = new ImgurObjectDaoImpl();
	}
	
	@Override
	public void downloadImgurObjectByHash(String hash) throws Exception {
		throw new Exception("To be implemented");
	}
	
	@Override
	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) throws Exception {
		String url;
		ImgurSubredditObjectsResponse response;
		LinkedHashSet<ImgurObjectAttrs> uniqueImgurObjects = new LinkedHashSet<>();
		
		int beforeListSize;
		int afterListSize;
		int count = 0;
		
		do {
			url = UrlUtil.constructImgurSubredditInfoUrl(imgurSearchQuery, count);
			this.logger.fine("Requesting info for " + url);
			count++;
			InputStream inputStream = httpClientService.getInputStreamForResource(url);
			response = new ObjectMapper().readValue(inputStream, new TypeReference<ImgurSubredditObjectsResponse>() { });
			beforeListSize = uniqueImgurObjects.size();
			uniqueImgurObjects.addAll(response.getData());
			afterListSize = uniqueImgurObjects.size();
		} while (beforeListSize != afterListSize);
		
		return new ArrayList<>(uniqueImgurObjects);
	}

	// TODO: Function is quite bulky, try to decompose it.
	@Override
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		String imgurObjectUrl;
		ImgurObjectDTO imgurObjectDTO;
		InputStream inputStream;
		String fileName;
		boolean isSaved;
		
		this.logger.fine(allImgurObjectAttrs.size() + " objects set to download");
		
		for (ImgurObjectAttrs imgurObjectAttrs : allImgurObjectAttrs) {
			List<ImgurObjectDTO> imgurObjectDTOList = repository.findByObjecthash(imgurObjectAttrs.getHash());
			
			if (imgurObjectDTOList.size() > 0 && isDownloaded(imgurObjectAttrs, imgurObjectDTOList)) {
				this.logger.info("Object with hash " + imgurObjectAttrs.getHash() + " already downloaded.");
				continue;
			}
			this.logger.info("Downloading " + imgurObjectAttrs.getTitle());
			imgurObjectDTO = TranslateObjects.getImgurObjectDTO(imgurObjectAttrs);
			imgurObjectUrl = UrlUtil.constructDownloadUrl(imgurObjectAttrs.getHash(), imgurObjectAttrs.getExt());
			fileName = fileService.replaceIllegalUrlChars(imgurObjectAttrs.getTitle() + imgurObjectAttrs.getExt());
			try {
                inputStream = httpClientService.getInputStreamForResource(imgurObjectUrl);
				isSaved = fileService.saveImgurObjectAsFile(imgurObjectAttrs.getSubreddit(), fileName, inputStream);
				int saveStatus = isSaved ? SaveStatus.SAVED.getId() : SaveStatus.FAILED.getId();
                imgurObjectDTO.setSavedstatus(saveStatus);
				repository.save(imgurObjectDTO);
			} catch (Exception ex) {
				imgurObjectDTO.setSavedstatus(SaveStatus.FAILED.getId());
				repository.save(imgurObjectDTO);
				this.logger.severe("Unable to save object " + imgurObjectUrl + ". Cause: " + ex.getMessage());
			}
		}
	}
	
	@Override
	public void poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs) throws Exception {
		List<List<ImgurObjectAttrs>> choppedList = chopImgurObjList(allImgurObjectAttrs);

		ExecutorService pool = Executors.newFixedThreadPool(Constants.DEFAULT_NO_OF_DOWNLOAD_THREADS);
        for (List<ImgurObjectAttrs> imgurObjectAttrsList : choppedList) {
            pool.submit(() -> downloadAllImgurObjectsInSubreddit(imgurObjectAttrsList));
        }

        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	}

	@Override
	public List<ImgurObjectAttrs> searchLocal(String title, String reddit) {
		List<ImgurObjectDTO> dtos = repository.search(title, reddit);
		List<ImgurObjectAttrs> attrs = new ArrayList<>();
		for (ImgurObjectDTO dto : dtos) {
			attrs.add(TranslateObjects.getImgurObject(dto));
		}
		return attrs;
	}

	private boolean isDownloaded(ImgurObjectAttrs imgurObjectAttrs,
								 List<ImgurObjectDTO> imgurObjectDTOList) {
		boolean isDownloaded = false;
		for (ImgurObjectDTO dto : imgurObjectDTOList) {
			if (dto.getSavedstatus() == SaveStatus.SAVED.getId() &&
					dto.getSize().intValue() == imgurObjectAttrs.getSize().intValue()) {
				isDownloaded = true;
				break;
			}
		}
		return isDownloaded;
	}
	
	private List<List<ImgurObjectAttrs>> chopImgurObjList(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		List<List<ImgurObjectAttrs>> choppedList = new ArrayList<>();
		int chunkSize = Constants.DEFAULT_LIST_CHOP_SIZE;
		
		for (int start = 0; start < allImgurObjectAttrs.size(); start += chunkSize) {
	        int end = Math.min(start + chunkSize, allImgurObjectAttrs.size());
	        List<ImgurObjectAttrs> sublist = allImgurObjectAttrs.subList(start, end);
	        choppedList.add(sublist);
	    }
		
		return choppedList;
	}
	
}
