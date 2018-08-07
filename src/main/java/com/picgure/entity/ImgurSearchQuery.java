package com.picgure.entity;

public class ImgurSearchQuery {
	
	private String redditName;
	
	private String sortOrder;

	public ImgurSearchQuery(String redditName, String sortOrder) {
		this.redditName = redditName;
		this.sortOrder = sortOrder;
	}

	public String getRedditName() {
		return redditName;
	}

	public void setRedditName(String redditName) {
		this.redditName = redditName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "ImgurSearchQuery{" +
				"redditName='" + redditName + '\'' +
				", sortOrder='" + sortOrder + '\'' +
				'}';
	}
}
