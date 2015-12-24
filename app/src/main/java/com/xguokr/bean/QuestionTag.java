package com.xguokr.bean;

import java.io.Serializable;

public class QuestionTag implements Serializable{

	private String TagName;
	private String TagImgUrl;
	private String TagDesc;
	private String TagNum;
	private String index;

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getTagName() {
		return TagName;
	}
	public void setTagName(String tagName) {
		TagName = tagName;
	}
	public String getTagImgUrl() {
		return TagImgUrl;
	}
	public void setTagImgUrl(String tagImgUrl) {
		TagImgUrl = tagImgUrl;
	}
	public String getTagDesc() {
		return TagDesc;
	}
	public void setTagDesc(String tagDesc) {
		TagDesc = tagDesc;
	}
	public String getTagNum() {
		return TagNum;
	}
	public void setTagNum(String tagNum) {
		TagNum = tagNum;
	}
	
}
