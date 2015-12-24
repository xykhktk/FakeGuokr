package com.xguokr.bean;

import java.io.Serializable;

public class KexuerenArticle implements Serializable {

	private static final long serialVersionUID = -3809778617192110395L;
	
	private String time;
	private String Author;
	private String AuthorImgUrl;
	private String ArticleContent;
	private String titile;
	private String ArticleImgUrl;
	private String ArticleID;
	
	
	public String getArticleID() {
		return ArticleID;
	}
	public void setArticleID(String articleID) {
		ArticleID = articleID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getAuthorImgUrl() {
		return AuthorImgUrl;
	}
	public void setAuthorImgUrl(String authorImgUrl) {
		AuthorImgUrl = authorImgUrl;
	}
	public String getArticleContent() {
		return ArticleContent;
	}
	public void setArticleContent(String articleContent) {
		ArticleContent = articleContent;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getArticleImgUrl() {
		return ArticleImgUrl;
	}
	public void setArticleImgUrl(String articleImgUrl) {
		ArticleImgUrl = articleImgUrl;
	}
	
}
