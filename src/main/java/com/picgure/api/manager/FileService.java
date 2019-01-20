package com.picgure.api.manager;

import com.picgure.entity.ImgurObjectAttrs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
 * @author Jeet Prakash
 * */
public interface FileService {

	public boolean saveImgurObjectAsFile(ImgurObjectAttrs imgurObject,
                                         InputStream is) throws IOException;

	public File defaultImageStoreDirectory();

	public void createImageStoreDirectory(File imageStore);

}
