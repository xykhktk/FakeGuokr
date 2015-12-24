package com.xguokr.bean;

import java.io.Serializable;

/**
 * Created on 2015/12/11.
 */
public class QuestionArticleContent implements Serializable{

    private String annotation_html;
    private String question;
    private String author_nickename;
    private String author_avatar;
    private String date_created;
    private String followers_count;
    private String answers_count;
    private String summary;
    private String id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnnotation_html(String annotation_html) {
        this.annotation_html = annotation_html;
    }

    public void setAuthor_nickename(String author_nickename) {
        this.author_nickename = author_nickename;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public void setAnswers_count(String answers_count) {
        this.answers_count = answers_count;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnotation_html() {
        return annotation_html;
    }

    public String getAuthor_nickename() {
        return author_nickename;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public String getAnswers_count() {
        return answers_count;
    }

    public String getSummary() {
        return summary;
    }

    public String getId() {
        return id;
    }
}
