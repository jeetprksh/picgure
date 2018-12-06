package com.picgure.api.thread;

import com.picgure.entity.ImgurObjectAttrs;

/*
 * @author Jeet Prakash
 * */
public class DownloadResult {

    private ImgurObjectAttrs imgurObject;
    private boolean isSaved;
    private Exception exception;

    public DownloadResult(ImgurObjectAttrs imgurObject, boolean isSaved, Exception exception) {
        this.imgurObject = imgurObject;
        this.isSaved = isSaved;
        this.exception = exception;
    }

    public ImgurObjectAttrs getImgurObject() {
        return imgurObject;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public Exception getException() {
        return exception;
    }
}
