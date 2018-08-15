package com.picgure.api.util;

import com.picgure.entity.ImgurObjectAttrs;
import com.picgure.persistence.dto.ImgurObjectDTO;

public class TranslateObjects {

    public static ImgurObjectDTO getImgurObjectDTO(ImgurObjectAttrs attrs) {
        ImgurObjectDTO dto = new ImgurObjectDTO();

        // dto.setDatecreated(attrs.getCreateDatetime());		// TODO get it working
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
        dto.setTitle(attrs.getTitle());
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
        attr.setCreateDatetime(null);
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
}
