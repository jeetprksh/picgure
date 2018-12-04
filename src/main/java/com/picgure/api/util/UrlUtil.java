package com.picgure.api.util;

import com.picgure.entity.ImgurSearchQuery;

/*
 * @author Jeet Prakash
 * */
public class UrlUtil {

    public static String constructImgurSubredditInfoUrl(ImgurSearchQuery imgurSearchQuery, Integer index) {
        // http://imgur.com/r/right/new/page/234.json
        return Constants.IMGUR_API_SUBREDDIT_INFO_BASE_URL + Constants.FILE_SEPERATOR + Constants.IMGUR_BASE +
                Constants.FILE_SEPERATOR + imgurSearchQuery.getRedditName() + Constants.FILE_SEPERATOR +
                imgurSearchQuery.getSortOrder() + Constants.FILE_SEPERATOR + "page" + Constants.FILE_SEPERATOR +
                index.toString() + ".json";
    }

    public static String constructDownloadUrl(String hash, String ext) {
        return Constants.IMGUR_OBJECT_DOWNLOAD_BASE_URL + Constants.FILE_SEPERATOR + hash + ext;
    }

}
