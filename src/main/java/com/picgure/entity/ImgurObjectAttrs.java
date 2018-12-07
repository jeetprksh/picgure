package com.picgure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "hash",
    "author",
    "account_id",
    "account_url",
    "title",
    "score",
    "size",
    "views",
    "is_album",
    "album_cover",
    "album_cover_width",
    "album_cover_height",
    "mimetype",
    "ext",
    "width",
    "height",
    "animated",
    "looping",
    "reddit",
    "subreddit",
    "description",
    "create_datetime",
    "bandwidth",
    "timestamp",
    "section",
    "nsfw",
    "prefer_video",
    "video_source",
    "video_host",
    "num_images",
    "favorited"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurObjectAttrs {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("author")
    private String author;
    @JsonProperty("account_id")
    private Object accountId;
    @JsonProperty("account_url")
    private Object accountUrl;
    @JsonProperty("title")
    private String title;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("views")
    private Integer views;
    @JsonProperty("is_album")
    private Boolean isAlbum;
    @JsonProperty("album_cover")
    private Object albumCover;
    @JsonProperty("album_cover_width")
    private Integer albumCoverWidth;
    @JsonProperty("album_cover_height")
    private Integer albumCoverHeight;
    @JsonProperty("mimetype")
    private String mimetype;
    @JsonProperty("ext")
    private String ext;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("animated")
    private Boolean animated;
    @JsonProperty("looping")
    private Boolean looping;
    @JsonProperty("reddit")
    private String reddit;
    @JsonProperty("subreddit")
    private String subreddit;
    @JsonProperty("description")
    private String description;
    @JsonProperty("create_datetime")
    private String createDatetime;
    @JsonProperty("bandwidth")
    private String bandwidth;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("section")
    private String section;
    @JsonProperty("nsfw")
    private Boolean nsfw;
    @JsonProperty("prefer_video")
    private Boolean preferVideo;
    @JsonProperty("video_source")
    private Object videoSource;
    @JsonProperty("video_host")
    private Object videoHost;
    @JsonProperty("num_images")
    private Integer numImages;
    @JsonProperty("favorited")
    private Boolean favorited;

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The hash
     */
    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    /**
     * 
     * @param hash
     *     The hash
     */
    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * 
     * @return
     *     The author
     */
    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The accountId
     */
    @JsonProperty("account_id")
    public Object getAccountId() {
        return accountId;
    }

    /**
     * 
     * @param accountId
     *     The account_id
     */
    @JsonProperty("account_id")
    public void setAccountId(Object accountId) {
        this.accountId = accountId;
    }

    /**
     * 
     * @return
     *     The accountUrl
     */
    @JsonProperty("account_url")
    public Object getAccountUrl() {
        return accountUrl;
    }

    /**
     * 
     * @param accountUrl
     *     The account_url
     */
    @JsonProperty("account_url")
    public void setAccountUrl(Object accountUrl) {
        this.accountUrl = accountUrl;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The score
     */
    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The score
     */
    @JsonProperty("score")
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The size
     */
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The views
     */
    @JsonProperty("views")
    public Integer getViews() {
        return views;
    }

    /**
     * 
     * @param views
     *     The views
     */
    @JsonProperty("views")
    public void setViews(Integer views) {
        this.views = views;
    }

    /**
     * 
     * @return
     *     The isAlbum
     */
    @JsonProperty("is_album")
    public Boolean getIsAlbum() {
        return isAlbum;
    }

    /**
     * 
     * @param isAlbum
     *     The is_album
     */
    @JsonProperty("is_album")
    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    /**
     * 
     * @return
     *     The albumCover
     */
    @JsonProperty("album_cover")
    public Object getAlbumCover() {
        return albumCover;
    }

    /**
     * 
     * @param albumCover
     *     The album_cover
     */
    @JsonProperty("album_cover")
    public void setAlbumCover(Object albumCover) {
        this.albumCover = albumCover;
    }

    /**
     * 
     * @return
     *     The albumCoverWidth
     */
    @JsonProperty("album_cover_width")
    public Integer getAlbumCoverWidth() {
        return albumCoverWidth;
    }

    /**
     * 
     * @param albumCoverWidth
     *     The album_cover_width
     */
    @JsonProperty("album_cover_width")
    public void setAlbumCoverWidth(Integer albumCoverWidth) {
        this.albumCoverWidth = albumCoverWidth;
    }

    /**
     * 
     * @return
     *     The albumCoverHeight
     */
    @JsonProperty("album_cover_height")
    public Integer getAlbumCoverHeight() {
        return albumCoverHeight;
    }

    /**
     * 
     * @param albumCoverHeight
     *     The album_cover_height
     */
    @JsonProperty("album_cover_height")
    public void setAlbumCoverHeight(Integer albumCoverHeight) {
        this.albumCoverHeight = albumCoverHeight;
    }

    /**
     * 
     * @return
     *     The mimetype
     */
    @JsonProperty("mimetype")
    public String getMimetype() {
        return mimetype;
    }

    /**
     * 
     * @param mimetype
     *     The mimetype
     */
    @JsonProperty("mimetype")
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    /**
     * 
     * @return
     *     The ext
     */
    @JsonProperty("ext")
    public String getExt() {
        return ext;
    }

    /**
     * 
     * @param ext
     *     The ext
     */
    @JsonProperty("ext")
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 
     * @return
     *     The width
     */
    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The animated
     */
    @JsonProperty("animated")
    public Boolean getAnimated() {
        return animated;
    }

    /**
     * 
     * @param animated
     *     The animated
     */
    @JsonProperty("animated")
    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    /**
     * 
     * @return
     *     The looping
     */
    @JsonProperty("looping")
    public Boolean getLooping() {
        return looping;
    }

    /**
     * 
     * @param looping
     *     The looping
     */
    @JsonProperty("looping")
    public void setLooping(Boolean looping) {
        this.looping = looping;
    }

    /**
     * 
     * @return
     *     The reddit
     */
    @JsonProperty("reddit")
    public String getReddit() {
        return reddit;
    }

    /**
     * 
     * @param reddit
     *     The reddit
     */
    @JsonProperty("reddit")
    public void setReddit(String reddit) {
        this.reddit = reddit;
    }

    /**
     * 
     * @return
     *     The subreddit
     */
    @JsonProperty("subreddit")
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * 
     * @param subreddit
     *     The subreddit
     */
    @JsonProperty("subreddit")
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The createDatetime
     */
    @JsonProperty("create_datetime")
    public String getCreateDatetime() {
        return createDatetime;
    }

    /**
     * 
     * @param createDatetime
     *     The create_datetime
     */
    @JsonProperty("create_datetime")
    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * 
     * @return
     *     The bandwidth
     */
    @JsonProperty("bandwidth")
    public String getBandwidth() {
        return bandwidth;
    }

    /**
     * 
     * @param bandwidth
     *     The bandwidth
     */
    @JsonProperty("bandwidth")
    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return
     *     The section
     */
    @JsonProperty("section")
    public String getSection() {
        return section;
    }

    /**
     * 
     * @param section
     *     The section
     */
    @JsonProperty("section")
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * 
     * @return
     *     The nsfw
     */
    @JsonProperty("nsfw")
    public Boolean getNsfw() {
        return nsfw;
    }

    /**
     * 
     * @param nsfw
     *     The nsfw
     */
    @JsonProperty("nsfw")
    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    /**
     * 
     * @return
     *     The preferVideo
     */
    @JsonProperty("prefer_video")
    public Boolean getPreferVideo() {
        return preferVideo;
    }

    /**
     * 
     * @param preferVideo
     *     The prefer_video
     */
    @JsonProperty("prefer_video")
    public void setPreferVideo(Boolean preferVideo) {
        this.preferVideo = preferVideo;
    }

    /**
     * 
     * @return
     *     The videoSource
     */
    @JsonProperty("video_source")
    public Object getVideoSource() {
        return videoSource;
    }

    /**
     * 
     * @param videoSource
     *     The video_source
     */
    @JsonProperty("video_source")
    public void setVideoSource(Object videoSource) {
        this.videoSource = videoSource;
    }

    /**
     * 
     * @return
     *     The videoHost
     */
    @JsonProperty("video_host")
    public Object getVideoHost() {
        return videoHost;
    }

    /**
     * 
     * @param videoHost
     *     The video_host
     */
    @JsonProperty("video_host")
    public void setVideoHost(Object videoHost) {
        this.videoHost = videoHost;
    }

    /**
     * 
     * @return
     *     The numImages
     */
    @JsonProperty("num_images")
    public Integer getNumImages() {
        return numImages;
    }

    /**
     * 
     * @param numImages
     *     The num_images
     */
    @JsonProperty("num_images")
    public void setNumImages(Integer numImages) {
        this.numImages = numImages;
    }

    /**
     * 
     * @return
     *     The favorited
     */
    @JsonProperty("favorited")
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * 
     * @param favorited
     *     The favorited
     */
    @JsonProperty("favorited")
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImgurObjectAttrs)) return false;
        ImgurObjectAttrs that = (ImgurObjectAttrs) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getHash(), that.getHash()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getSize(), that.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHash(), getTitle(), getSize());
    }

    @Override
    public String toString() {
        return "ImgurObjectAttrs{" +
                "hash='" + hash + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", description='" + description + '\'' +
                ", createDatetime='" + createDatetime + '\'' +
                '}';
    }
}
