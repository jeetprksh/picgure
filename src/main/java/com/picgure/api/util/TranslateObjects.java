package com.picgure.api.util;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.persistence.dto.ImgurObjectDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * @author Jeet Prakash
 * */
public class TranslateObjects {

    public static final int IMGUR_OBJECT_MAX_TITLE_LENTH = 250;

    public static ImgurObjectDTO getImgurObjectDTO(ImgurObjectAttrs attrs) {
        ImgurObjectDTO dto = new ImgurObjectDTO();

        dto.setDatecreated(getTimestamp(attrs.getCreateDatetime()));
        dto.setAuthor(attrs.getAuthor());
        dto.setDescription(attrs.getDescription());
        dto.setExtension(attrs.getExt());
        dto.setHeight(attrs.getHeight());
        dto.setIsanimated(attrs.getAnimated());
        dto.setMimetype(attrs.getMimetype());
        dto.setObjecthash(attrs.getHash());
        dto.setObjectid(attrs.getId());
        dto.setSection(attrs.getSection());
        dto.setReddit(attrs.getReddit());
        dto.setSize(attrs.getSize());
        dto.setSubreddit(attrs.getSubreddit());
        dto.setTitle(getTruncatedTitle(attrs.getTitle()));
        dto.setWidth(attrs.getWidth());

        return dto;
    }

    public static ImgurObjectAttrs getImgurObject(ImgurObjectDTO dto) {
        ImgurObjectAttrs attr = new ImgurObjectAttrs();

        attr.setAccountId(null);
        attr.setAccountUrl(null);
        attr.setAuthor(dto.getAuthor());
        attr.setAlbumCoverHeight(null);
        attr.setAlbumCover(null);
        attr.setAlbumCoverWidth(null);
        attr.setAnimated(dto.getIsanimated());
        attr.setBandwidth(null);
        attr.setCreateDatetime(dto.getDatecreated().toLocalDateTime().toString());
        attr.setDescription(dto.getDescription());
        attr.setExt(dto.getExtension());
        attr.setFavorited(null);
        attr.setHash(dto.getObjecthash());
        attr.setHeight(dto.getHeight());
        attr.setId(dto.getObjectid());
        attr.setIsAlbum(null);
        attr.setLooping(null);
        attr.setMimetype(dto.getMimetype());
        attr.setNsfw(null);
        attr.setNumImages(null);
        attr.setPreferVideo(null);
        attr.setReddit(dto.getReddit());
        attr.setScore(null);
        attr.setSection(dto.getSection());
        attr.setSize(dto.getSize());
        attr.setSubreddit(dto.getSubreddit());
        attr.setTimestamp(null);
        attr.setTitle(dto.getTitle());
        attr.setViews(null);
        attr.setVideoHost(null);
        attr.setVideoSource(null);
        attr.setWidth(dto.getWidth());

        return attr;
    }

    private static String getTruncatedTitle(String title) {
        return title.substring(0, Math.min(title.length(), IMGUR_OBJECT_MAX_TITLE_LENTH));
    }

    private static Timestamp getTimestamp(String date) {
        // "2013-05-20 21:08:52" -> "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, df);
        return Timestamp.valueOf(dateTime);
    }
}
