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
import com.picgure.persistence.dao.ImgurObjectDao;
import com.picgure.persistence.dao.impl.ImgurObjectDaoImpl;
import com.picgure.persistence.dto.ImgurObjectDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ObjectServiceImpl implements ObjectService {
	
	private Logger logger = Logger.getLogger(ObjectServiceImpl.class.getName());

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
		// TODO
	}
	
	@Override
	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) {
		
		String url;
		ImgurSubredditObjectsResponse response;
		List<ImgurObjectAttrs> allImgurObjectAttrs = new ArrayList<>();
		
		int beforeListSize = 0;
		int afterListSize = 0;
		int count = 0;
		
		do {
			url = UrlUtil.constructImgurSubredditInfoUrl(imgurSearchQuery, count);
			this.logger.info("REQUESTING INFO FOR :: " + url);
			count++;
			try {
				InputStream inputStream = httpClientService.getInputStreamForResource(url);
				response = new ObjectMapper().readValue(inputStream, new TypeReference<ImgurSubredditObjectsResponse>() {/*noop*/});
				beforeListSize = allImgurObjectAttrs.size();
				allImgurObjectAttrs = addUniqueImgurObjects(allImgurObjectAttrs, response.getData());
				this.logger.info("Objects found :: " + response.getData().size());
				afterListSize = allImgurObjectAttrs.size();
				this.logger.info("SIZES :: " + beforeListSize + "  " + afterListSize);
			} catch (IOException e) {
				this.logger.severe("Exception in listing objects at URL: " + url);
				e.printStackTrace();
			}
		} while (beforeListSize != afterListSize);
		
		if (imgurSearchQuery.getSortOrder().equalsIgnoreCase("new")) {
			Collections.reverse(allImgurObjectAttrs);
		}
		
		return allImgurObjectAttrs;
	}

	// TODO: Function is quite bulky, try to decompose it.
	@Override
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		
		String imgurObjectUrl;
		boolean isSaved;
		InputStream inputStream;
		
		this.logger.info("DOWNLOADING THE OBJECTS :: " + allImgurObjectAttrs.size());
		
		for (ImgurObjectAttrs imgurObjectAttrs : allImgurObjectAttrs) {
			
			List<ImgurObjectDTO> imgurObjectDTOList = repository.findByObjecthash(imgurObjectAttrs.getHash());
			
			if (imgurObjectDTOList.size()>0 && isObjectAlreadyDownloaded(imgurObjectAttrs, imgurObjectDTOList)) {
				this.logger.info("Imgur object with hash " + imgurObjectAttrs.getHash() + " already downloaded.");
				continue;
			}
			this.logger.info("DOWNLOADING :: " + imgurObjectAttrs.getTitle());
			ImgurObjectDTO  imgurObjectDTO = new ImgurObjectDTO();
			imgurObjectUrl = UrlUtil.constructImgurObjectDownloadUrl(imgurObjectAttrs.getHash(), imgurObjectAttrs.getExt());
			try {
				imgurObjectDTO = TranslateObjects.getImgurObjectDTO(imgurObjectAttrs);

				String cleanedFileName = fileService.replaceIllegalCharsInFileName(imgurObjectAttrs.getTitle() + imgurObjectAttrs.getExt());
				inputStream = httpClientService.getInputStreamForResource(imgurObjectUrl);
				isSaved = fileService.saveImgurObjectAsFile(imgurObjectAttrs.getSubreddit(), cleanedFileName, inputStream);
				
				if (isSaved) {
					imgurObjectDTO.setSavedstatus(SaveStatus.SAVED.getId());
				} else {
					imgurObjectDTO.setSavedstatus(SaveStatus.FAILED.getId());
				}
				
				repository.save(imgurObjectDTO);
			} catch (Exception e) {
				e.printStackTrace();
				// save DTO with failed status.
				imgurObjectDTO.setSavedstatus(SaveStatus.FAILED.getId());
				repository.save(imgurObjectDTO);
				this.logger.info("Error occured in getting/saving resource :: " + imgurObjectUrl);
			}
		}
		
	}
	
	@Override
	public void poolDownloadObjects(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		
		List<List<ImgurObjectAttrs>> choppedList = chopImgurObjList(allImgurObjectAttrs, Constants.DEFAULT_LIST_CHOP_SIZE);
		
		try {
			ExecutorService pool = Executors.newFixedThreadPool(Constants.DEFAULT_NO_OF_DOWNLOAD_THREADS);
			for (List<ImgurObjectAttrs> imgurObjectAttrsList : choppedList) {
				pool.submit(() -> downloadAllImgurObjectsInSubreddit(imgurObjectAttrsList));
			}
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<ImgurObjectAttrs> searchLocalRepoByTitleAndReddit(String title, String reddit) {
		List<ImgurObjectDTO> dtos = repository.search(title, reddit);
		List<ImgurObjectAttrs> attrs = new ArrayList<>();
		for (ImgurObjectDTO dto : dtos) {
			attrs.add(TranslateObjects.getImgurObject(dto));
		}
		return attrs;
	}

	private boolean isObjectAlreadyDownloaded(ImgurObjectAttrs imgurObjectAttrs, 
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
	
	private List<ImgurObjectAttrs> addUniqueImgurObjects(List<ImgurObjectAttrs> targetList,
			List<ImgurObjectAttrs> imgurObjectAttrsList) {
		boolean isUnique;
		
		// To handle the trivial case of first invocation 
		// of this function when targetList can be empty.
		if (targetList.size() == 0) {
			return imgurObjectAttrsList;
		}
		
		// Copy the object in the targetList to a temporary list as adding/removing 
		// into the list that we are also traversing results in ConcurrentModificationException.
		List<ImgurObjectAttrs> tempList = new ArrayList<>();
		for (ImgurObjectAttrs objectAttrs : targetList) {
			tempList.add(objectAttrs);
		}
		
		for (ImgurObjectAttrs imgurObjectAttrs : imgurObjectAttrsList) {
			isUnique = true;
			for (ImgurObjectAttrs imgurObjectInTempList : tempList) {
				if (imgurObjectAttrs.equals(imgurObjectInTempList)) {
					this.logger.info(imgurObjectAttrs.getHash() + ",");
					isUnique = false;
					break;
				}
			}
			if (isUnique) {
				targetList.add(imgurObjectAttrs);
			}
		}
		return targetList;
	}
	
	private List<List<ImgurObjectAttrs>> chopImgurObjList(
			List<ImgurObjectAttrs> allImgurObjectAttrs, int chunkSize) {
		
		List<List<ImgurObjectAttrs>> choppedList = new ArrayList<>();
		
		for (int start = 0; start < allImgurObjectAttrs.size(); start += chunkSize) {
	        int end = Math.min(start + chunkSize, allImgurObjectAttrs.size());
	        List<ImgurObjectAttrs> sublist = allImgurObjectAttrs.subList(start, end);
	        choppedList.add(sublist);
	    }
		
		return choppedList;
	}
	
}
