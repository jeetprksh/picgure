package com.picgure.api.util;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.entity.ImgurSearchQuery;

/*
 * @author Jeet Prakash
 * */
public class UrlUtil {

    private static final String IMGUR_API_SUBREDDIT_INFO_BASE_URL = "https://imgur.com";
    private static final String IMGUR_OBJECT_DOWNLOAD_BASE_URL = "http://i.imgur.com";
    private static final String IMGUR_BASE = "r";
    private static final String FILE_SEPARATOR = "/";

    public static String constructSubredditInfoUrl(ImgurSearchQuery imgurSearchQuery, Integer index) {
        // http://imgur.com/r/right/new/page/234.json
        return IMGUR_API_SUBREDDIT_INFO_BASE_URL + FILE_SEPARATOR +
                IMGUR_BASE + FILE_SEPARATOR +
                imgurSearchQuery.getRedditName() + FILE_SEPARATOR +
                imgurSearchQuery.getSortOrder() + FILE_SEPARATOR +
                "page" + FILE_SEPARATOR +
                index.toString() + ".json";
    }

    public static String constructObjectDownloadUrl(ImgurObjectAttrs imgurObject) {
        return IMGUR_OBJECT_DOWNLOAD_BASE_URL + FILE_SEPARATOR + imgurObject.getHash() + imgurObject.getExt();
    }

}
