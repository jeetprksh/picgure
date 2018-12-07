package com.picgure.api.thread;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.HttpClientService;
import com.picgure.api.manager.impl.FileServiceImpl;
import com.picgure.api.manager.impl.ObjectServiceImpl;
import com.picgure.api.util.SaveStatus;
import com.picgure.api.util.TranslateObjects;
import com.picgure.api.util.UrlUtil;
import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.logging.PicgureLogger;
import com.picgure.persistence.dao.ImgurObjectDao;
import com.picgure.persistence.dto.ImgurObjectDTO;

import java.io.InputStream;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ObjectDownloader implements Runnable {

    private Logger logger = PicgureLogger.getLogger(ObjectServiceImpl.class);

    private ImgurObjectAttrs imgurObject;
    private HttpClientService httpClientService;
    private FileService fileService;
    private ImgurObjectDao repository;
    private DownloadProgress progress;

    public ObjectDownloader(ImgurObjectAttrs imgurObject,
                            HttpClientService httpClientService,
                            ImgurObjectDao repository,
                            DownloadProgress progress) {
        this.imgurObject = imgurObject;
        this.fileService = new FileServiceImpl();
        this.httpClientService = httpClientService;
        this.repository = repository;
        this.progress = progress;
    }

    @Override
    public void run() {
        this.logger.info("Downloading " + imgurObject.getTitle());
        ImgurObjectDTO imgurObjectDTO = TranslateObjects.getImgurObjectDTO(imgurObject);
        String imgurObjectUrl = UrlUtil.constructObjectDownloadUrl(imgurObject);
        try {
            InputStream is = httpClientService.getInputStreamForUrl(imgurObjectUrl);
            fileService.saveImgurObjectAsFile(imgurObject, is);
            imgurObjectDTO.setSavedstatus(SaveStatus.SAVED.getId());
            progress.addResult(new DownloadResult(imgurObject, true, null));
        } catch (Exception ex) {
            imgurObjectDTO.setSavedstatus(SaveStatus.FAILED.getId());
            this.logger.severe("Unable to download object " + imgurObjectUrl + ". Cause: " + ex.getMessage());
            progress.addResult(new DownloadResult(imgurObject, false, ex));
        }
        repository.save(imgurObjectDTO);
    }

}
