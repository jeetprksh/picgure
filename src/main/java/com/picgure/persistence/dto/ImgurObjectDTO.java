package com.picgure.persistence.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the imgurobjectstab database table.
 * 
 */
@Entity
@Table(name="imgurobjectstab")
@NamedQueries({ 
	@NamedQuery(name = "imgurObject.getAllImgurObjects", 
				query = "SELECT i FROM ImgurObjectDTO i"),
	@NamedQuery(name = "imgurObject.getImgurObjectByHash", 
				query = "SELECT i FROM ImgurObjectDTO i where i.objecthash = :hash"),
	@NamedQuery(name = "imgurObject.searchObjectsByTitle", 
				query = "SELECT i from ImgurObjectDTO i where UPPER(i.title) like :searchstring"),
	@NamedQuery(name = "imgurObject.getImgurObjectBySubreddit", 
				query = "SELECT i FROM ImgurObjectDTO i where i.subreddit = :subreddit")
})
public class ImgurObjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Timestamp dateadded;

	private Timestamp datecreated;

	private String description;

	private String extension;

	private Long height;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Boolean isanimated;

	private String mimetype;

	private String objecthash;

	private Long objectid;

	private String reddit;

	private Long size;

	private String subreddit;

	private String title;

	private Long width;
	
	private Integer downloadstatus;

	public ImgurObjectDTO() {
	}

	public Timestamp getDateadded() {
		return this.dateadded;
	}

	public void setDateadded(Timestamp dateadded) {
		this.dateadded = dateadded;
	}

	public Timestamp getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getHeight() {
		return this.height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsanimated() {
		return this.isanimated;
	}

	public void setIsanimated(Boolean isanimated) {
		this.isanimated = isanimated;
	}

	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getObjecthash() {
		return this.objecthash;
	}

	public void setObjecthash(String objecthash) {
		this.objecthash = objecthash;
	}

	public Long getObjectid() {
		return this.objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getReddit() {
		return this.reddit;
	}

	public void setReddit(String reddit) {
		this.reddit = reddit;
	}

	public Long getSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getSubreddit() {
		return this.subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}
	
	public int getDownloadstatus() {
		return downloadstatus;
	}

	public void setDownloadstatus(int downloadstatus) {
		this.downloadstatus = downloadstatus;
	}

}