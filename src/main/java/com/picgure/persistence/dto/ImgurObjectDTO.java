package com.picgure.persistence.dto;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the imgurobjectstab database table.
 * 
 */
@Entity
@Table(name="imgurobjectstab")
@NamedQueries({
		@NamedQuery(name = "imgurobjectstab.findByObjecthash",
					query = "select i from ImgurObjectDTO i where i.objecthash = :objectHash"),
		@NamedQuery(name = "imgurobjectstab.findBySubreddit",
					query = "select i from ImgurObjectDTO i where i.subreddit = :subreddit"),
		@NamedQuery(name = "imgurobjectstab.search",
					query = "select i FROM ImgurObjectDTO i WHERE i.title like :title or i.subreddit like :subreddit")
})
public class ImgurObjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String author;

	@CreationTimestamp
	private Timestamp dateDownloaded;

	private Timestamp datecreated;

	private String description;

	private String extension;

	private Integer height;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Boolean isanimated;

	private String mimetype;

	private String objecthash;

	private Long objectid;

	private String reddit;

	private int savedstatus;

	private String section;

	private Integer size;

	private String subreddit;

	private String title;

	private Integer width;

	public ImgurObjectDTO() {
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getDateDownloaded() {
		return this.dateDownloaded;
	}

	public void setDateDownloaded(Timestamp dateDownloaded) {
		this.dateDownloaded = dateDownloaded;
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

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
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

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public int getSavedstatus() {
		return savedstatus;
	}

	public void setSavedstatus(int savedstatus) {
		this.savedstatus = savedstatus;
	}

}