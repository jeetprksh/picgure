package com.picgure.api.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picgure.api.manager.HttpClientService;
import com.picgure.api.manager.ObjectService;
import com.picgure.api.thread.DownloadProgress;
import com.picgure.api.thread.ObjectDownloader;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * @author Jeet Prakash
 * */
public class ObjectServiceImpl implements ObjectService {

	private Logger logger = PicgureLogger.getLogger(ObjectServiceImpl.class);
	private static final int DEFAULT_NO_OF_DOWNLOAD_THREADS = 20;

	private HttpClientService httpClientService;
	private ImgurObjectDao repository;

	public ObjectServiceImpl() {
        this.httpClientService = new HttpClientServiceImpl();
        this.repository = new ImgurObjectDaoImpl();
	}

	@Override
	public void downloadImgurObjectByHash(String hash) throws Exception {
		throw new Exception("To be implemented");
	}

	@Override
	public List<ImgurObjectAttrs> getObjectsInSubreddit(ImgurSearchQuery imgurSearchQuery) throws Exception {
		String url;
        InputStream is;
		ImgurSubredditObjectsResponse response;
		LinkedHashSet<ImgurObjectAttrs> uniqueImgurObjects = new LinkedHashSet<>();

		int beforeListSize;
		int afterListSize;
		int count = 0;

		do {
			url = UrlUtil.constructSubredditInfoUrl(imgurSearchQuery, count);
			this.logger.fine("Requesting info for " + url);
			count++;
            is = httpClientService.getInputStreamForUrl(url);
			response = new ObjectMapper().readValue(is, new TypeReference<ImgurSubredditObjectsResponse>() { });
			beforeListSize = uniqueImgurObjects.size();
			uniqueImgurObjects.addAll(response.getData());
			afterListSize = uniqueImgurObjects.size();
		} while (beforeListSize != afterListSize);

		return new ArrayList<>(uniqueImgurObjects);
	}

	@Override
	public DownloadProgress poolDownloadObjects(List<ImgurObjectAttrs> imgurObjects) throws Exception {
        long size = imgurObjects.stream().mapToInt(ImgurObjectAttrs::getSize).sum();
        this.logger.fine(imgurObjects.size() + " objects set to download with overall size of " + size);

		List<ImgurObjectAttrs> toBeDownloaded = imgurObjects.stream()
				.filter(imgurObj -> !this.isDownloaded(imgurObj)).collect(Collectors.toList());

		if (toBeDownloaded.isEmpty())
			throw new Exception("All the objects in this subreddit are already downloaded.");

		ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_NO_OF_DOWNLOAD_THREADS);
        DownloadProgress progress = new DownloadProgress(toBeDownloaded.size());
		toBeDownloaded.forEach(imgurObject ->
				pool.submit(new ObjectDownloader(imgurObject, httpClientService, repository, progress)));

        pool.shutdown();
        return progress;
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

	private boolean isDownloaded(ImgurObjectAttrs imgurObject) {
		List<ImgurObjectDTO> imgurObjectDTOList = repository.findByObjecthash(imgurObject.getHash());
		boolean isDownloaded = false;
		for (ImgurObjectDTO dto : imgurObjectDTOList) {
			if (dto.getSavedstatus() == SaveStatus.SAVED.getId() &&
					dto.getSize().intValue() == imgurObject.getSize().intValue()) {
				isDownloaded = true;
				this.logger.info("Object with hash " + imgurObject.getHash() + " already downloaded.");
				break;
			}
		}
		return isDownloaded;
	}

}
