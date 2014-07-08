package com.osi.socialmedia.model;

import java.io.Serializable;

public class FeedsModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authorName;
	private String feedText;
	private String postedAt;
	private String messgFrom;
	private String messgText;
	private String messgDate;
	private String userImageurl;
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getFeedText() {
		return feedText;
	}
	public void setFeedText(String feedText) {
		this.feedText = feedText;
	}
	public String getPostedAt() {
		return postedAt;
	}
	public void setPostedAt(String postedAt) {
		this.postedAt = postedAt;
	}
	public String getMessgFrom() {
		return messgFrom;
	}
	public void setMessgFrom(String messgFrom) {
		this.messgFrom = messgFrom;
	}
	public String getMessgText() {
		return messgText;
	}
	public void setMessgText(String messgText) {
		this.messgText = messgText;
	}
	public String getMessgDate() {
		return messgDate;
	}
	public void setMessgDate(String messgDate) {
		this.messgDate = messgDate;
	}
	public String getUserImageurl() {
		return userImageurl;
	}
	public void setUserImageurl(String userImageurl) {
		this.userImageurl = userImageurl;
	}

}
