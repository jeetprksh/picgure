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
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ObjectDownloader implements Callable<DownloadResult> {

    private Logger logger = PicgureLogger.getLogger(ObjectServiceImpl.class);

    private ImgurObjectAttrs imgurObject;
    private HttpClientService httpClientService;
    private FileService fileService;
    private ImgurObjectDao repository;

    public ObjectDownloader(ImgurObjectAttrs imgurObject,
                            HttpClientService httpClientService,
                            ImgurObjectDao repository) {
        this.imgurObject = imgurObject;
        this.fileService = new FileServiceImpl();
        this.httpClientService = httpClientService;
        this.repository = repository;
    }

    @Override
    public DownloadResult call() throws Exception {
        this.logger.info("Downloading " + imgurObject.getTitle());
        ImgurObjectDTO imgurObjectDTO = TranslateObjects.getImgurObjectDTO(imgurObject);
        String imgurObjectUrl = UrlUtil.constructObjectDownloadUrl(imgurObject);
        try {
            InputStream is = httpClientService.getInputStreamForUrl(imgurObjectUrl);
            fileService.saveImgurObjectAsFile(imgurObject, is);
            imgurObjectDTO.setSavedstatus(SaveStatus.SAVED.getId());
            repository.save(imgurObjectDTO);
            return new DownloadResult(imgurObject, true, null);
        } catch (Exception ex) {
            imgurObjectDTO.setSavedstatus(SaveStatus.FAILED.getId());
            repository.save(imgurObjectDTO);
            this.logger.severe("Unable to save object " + imgurObjectUrl + ". Cause: " + ex.getMessage());
            return new DownloadResult(imgurObject, false, ex);
        }
    }

}
