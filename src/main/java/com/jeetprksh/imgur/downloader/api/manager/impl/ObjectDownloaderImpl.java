package com.jeetprksh.imgur.downloader.api.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jeetprksh.imgur.downloader.api.manager.ObjectDownloader;
import com.jeetprksh.imgur.downloader.api.manager.FileIO;
import com.jeetprksh.imgur.downloader.api.manager.HttpClient;
import com.jeetprksh.imgur.downloader.api.util.Constants;
import com.jeetprksh.imgur.downloader.api.util.DownloadStatus;
import com.jeetprksh.imgur.downloader.entity.ImgurObjectAttrs;
import com.jeetprksh.imgur.downloader.entity.ImgurSearchQuery;
import com.jeetprksh.imgur.downloader.entity.ImgurSubredditObjectsResponse;
import com.jeetprksh.imgur.downloader.persistence.dao.ImgurObjectsDAO;
import com.jeetprksh.imgur.downloader.persistence.dto.ImgurObjectDTO;

@Component
public class ObjectDownloaderImpl implements ObjectDownloader {

	@Autowired HttpClient httpClientMgmt;
	@Autowired FileIO fileMgmt;
	@Autowired ImgurObjectsDAO imgurObjectsDAO;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void downloadImgurObjectByHash(String hash) throws Exception {
		
	}
	
	@Override
	public List<ImgurObjectAttrs> listAllObjectInSubreddit(ImgurSearchQuery imgurSearchQuery) {
		
		String url;
		ImgurSubredditObjectsResponse response;
		List<ImgurObjectAttrs> allImgurObjectAttrs = Lists.newArrayList();
		
		// TODO Loop with no terminating condition
		for(int count=0; ; count++) {
			url = constructImgurSubredditInfoUrl(imgurSearchQuery, count);
			int beforeListSize;
			int afterListSize;
			System.out.print("REQUESTING INFO FOR :: " + url);
			try {
				InputStream resourceInputStream = httpClientMgmt.getInputStreamForResource(url);
				response = new ObjectMapper().readValue(resourceInputStream, 
						new TypeReference<ImgurSubredditObjectsResponse>() {/*noop*/});
				beforeListSize = allImgurObjectAttrs.size();
				allImgurObjectAttrs = addUniqueImgurObjects(allImgurObjectAttrs, response.getData());
				System.out.println("Objects found :: " + response.getData().size());
				afterListSize = allImgurObjectAttrs.size();
				System.out.println("SIZES :: " + beforeListSize + "  " + afterListSize);
				if (beforeListSize == afterListSize) {
					break;
				}
			} catch (IOException e) {
				System.out.println("Exception in listing objects at URL: " + url);
				e.printStackTrace();
				break;
			}
		}
		
		if (imgurSearchQuery.getSortOrder().equalsIgnoreCase("new")) {
			Collections.reverse(allImgurObjectAttrs);
		}
		
		return allImgurObjectAttrs;
	}

	// TODO: Function is quite bulky, try to decompose it.
	@Override
	@Transactional(rollbackFor=Exception.class, isolation=Isolation.READ_UNCOMMITTED, readOnly=false)
	public void downloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		
		String imgurObjectUrl;
		boolean isSaved;
		InputStream inputStream;
		
		System.out.println("DOWNLOADING THE OBJECTS :: " + allImgurObjectAttrs.size());
		
		for (ImgurObjectAttrs imgurObjectAttrs : allImgurObjectAttrs) {
			
			List<ImgurObjectDTO> imgurObjectDTOList = imgurObjectsDAO.getImgurObjectByHash(imgurObjectAttrs.getHash());
			
			if (imgurObjectDTOList.size()>0 && isObjectAlreadyDownloaded(imgurObjectAttrs, imgurObjectDTOList)) {
				System.out.println("Imgur object with hash " + imgurObjectAttrs.getHash() + " already downloaded.");
				continue;
			}
			System.out.println("DOWNLOADING :: " + imgurObjectAttrs.getTitle());
			ImgurObjectDTO  imgurObjectDTO = new ImgurObjectDTO();
			imgurObjectUrl = constructImgurObjectDownloadUrl(imgurObjectAttrs.getHash(), imgurObjectAttrs.getExt());
			try {
				imgurObjectDTO = populateImgurObjectDTO(imgurObjectAttrs);

				inputStream = httpClientMgmt.getInputStreamForResource(imgurObjectUrl);
				isSaved = fileMgmt.saveImgurObjectAsFile(imgurObjectAttrs.getSubreddit(), 
						fileMgmt.replaceIllegalCharsInFileName(imgurObjectAttrs.getTitle() + imgurObjectAttrs.getExt()), inputStream);
				
				if (isSaved) {
					imgurObjectDTO.setDownloadstatus(DownloadStatus.DOWNLOADED.getId());
				} else {
					imgurObjectDTO.setDownloadstatus(DownloadStatus.FAILED.getId());
				}
				
				imgurObjectsDAO.addImgurObject(imgurObjectDTO);
			} catch (Exception e) {
				// save DTO with failed status.
				imgurObjectDTO.setDownloadstatus(DownloadStatus.FAILED.getId());
				imgurObjectsDAO.addImgurObject(imgurObjectDTO);
				System.out.println("Error occured in getting/saving resource :: " + imgurObjectUrl);
			}
		}
		
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class, isolation=Isolation.READ_UNCOMMITTED, readOnly=false)
	public void poolDownloadAllImgurObjectsInSubreddit(List<ImgurObjectAttrs> allImgurObjectAttrs) {
		
		List<List<ImgurObjectAttrs>> choppedList = chopImgurObjList(allImgurObjectAttrs, Constants.DEFAULT_LIST_CHOP_SIZE);		// TODO make the chunk size dynamic
		
		try {
			ExecutorService pool = Executors.newFixedThreadPool(Constants.DEFAULT_NO_OF_DOWNLOAD_THREADS);				// TODO make the tread pool size dynamic
			for (List<ImgurObjectAttrs> imgurObjectAttrsList : choppedList) {
				pool.submit(() -> downloadAllImgurObjectsInSubreddit(imgurObjectAttrsList));
			}
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean isObjectAlreadyDownloaded(ImgurObjectAttrs imgurObjectAttrs, 
			List<ImgurObjectDTO> imgurObjectDTOList) {
		boolean isDownloaded = false;
		for (ImgurObjectDTO dto : imgurObjectDTOList) {
			if (dto.getDownloadstatus() == DownloadStatus.DOWNLOADED.getId() && dto.getTitle().equals(imgurObjectAttrs.getTitle()) && 
					dto.getSize().intValue() == imgurObjectAttrs.getSize().intValue()) {
				isDownloaded = true;
				break;
			}
		}
		return isDownloaded;
	}

	private ImgurObjectDTO populateImgurObjectDTO(ImgurObjectAttrs imgurObjectAttrs) {
		ImgurObjectDTO dto = new ImgurObjectDTO();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		dto.setDateadded(timestamp);
		//dto.setDatecreated(imgurObjectAttrs.getCreateDatetime());		// TODO get it working
		dto.setDescription(imgurObjectAttrs.getDescription());
		dto.setExtension(imgurObjectAttrs.getExt());
		dto.setHeight(imgurObjectAttrs.getHeight().longValue());
		dto.setIsanimated(imgurObjectAttrs.getAnimated());
		dto.setMimetype(imgurObjectAttrs.getMimetype());
		dto.setObjecthash(imgurObjectAttrs.getHash());
		dto.setObjectid(imgurObjectAttrs.getId().longValue());
		dto.setReddit(imgurObjectAttrs.getReddit());
		dto.setSize(imgurObjectAttrs.getSize().longValue());
		dto.setSubreddit(imgurObjectAttrs.getSubreddit());
		dto.setTitle(imgurObjectAttrs.getTitle());
		dto.setWidth(imgurObjectAttrs.getWidth().longValue());
		
		return dto;
	}
	
	private String constructImgurSubredditInfoUrl(ImgurSearchQuery imgurSearchQuery, Integer index) {
		
		// http://imgur.com/r/right/new/page/234.json
		return Constants.IMGUR_API_SUBREDDIT_INFO_BASE_URL + Constants.FILE_SEPERATOR + imgurSearchQuery.getBase() +
				Constants.FILE_SEPERATOR + imgurSearchQuery.getRedditName() + Constants.FILE_SEPERATOR +
				imgurSearchQuery.getSortOrder() + Constants.FILE_SEPERATOR + "page" + Constants.FILE_SEPERATOR +
				index.toString() + ".json";
		
	}
	
	private String constructImgurObjectDownloadUrl(String hash, String ext) {
		return Constants.IMGUR_OBJECT_DOWNLOAD_BASE_URL + Constants.FILE_SEPERATOR + hash + ext;
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
		List<ImgurObjectAttrs> tempList = Lists.newArrayList();
		for (ImgurObjectAttrs objectAttrs : targetList) {
			tempList.add(objectAttrs);
		}
		
		for (ImgurObjectAttrs imgurObjectAttrs : imgurObjectAttrsList) {
			isUnique = true;
			for (ImgurObjectAttrs imgurObjectInTempList : tempList) {
				if (imgurObjectAttrs.equals(imgurObjectInTempList)) {
					System.out.print(imgurObjectAttrs.getHash() + ",");
					isUnique = false;
					break;
				}
			}
			if (isUnique) {
				targetList.add(imgurObjectAttrs);
			}
		}
		System.out.println();
		return targetList;
	}
	
	private List<List<ImgurObjectAttrs>> chopImgurObjList(
			List<ImgurObjectAttrs> allImgurObjectAttrs, int chunkSize) {
		
		System.out.println("CHOPPING THE LIST");
		
		List<List<ImgurObjectAttrs>> choppedList = Lists.newArrayList();
		
		for (int start = 0; start < allImgurObjectAttrs.size(); start += chunkSize) {
	        int end = Math.min(start + chunkSize, allImgurObjectAttrs.size());
	        List<ImgurObjectAttrs> sublist = allImgurObjectAttrs.subList(start, end);
	        choppedList.add(sublist);
	    }
		
		return choppedList;
	}
	
}


class ChunkTest {
	public static void main(String[] args) {
		List<String> arrayList = new ArrayList<String>();
	    for (int i = 0; i < 23; i++) {
	        arrayList.add(String.valueOf(i));
	    }
	    
	    List<List<String>> choppedList = ChunkTest.chopImgurObjList(arrayList, 10);
	    
	    for (List<String> strList : choppedList) {
	    	System.out.println("CHOPPED LIS SIZE :: " + strList.size());
	    	
	    	for (String str : strList) {
	    		System.out.println(str);
	    	}
	    }
	}
	
	private static List<List<String>> chopImgurObjList(List<String> allImgurObjectAttrs, int chunkSize) {
		
		List<List<String>> choppedList = Lists.newArrayList();
		
		for (int start = 0; start < allImgurObjectAttrs.size(); start += chunkSize) {
	        int end = Math.min(start + chunkSize, allImgurObjectAttrs.size());
	        List<String> sublist = allImgurObjectAttrs.subList(start, end);
	        choppedList.add(sublist);
	    }
		
		return choppedList;
	}
}
