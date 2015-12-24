package com.xguokr.bean;

import java.io.Serializable;

/**
 * Created by yk on 2015/12/11.
 */
public class ReplyItem implements Serializable{

    private String author_Nickename;
    private String author_avatar;
    private String date_created;
    private String html;
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAuthor_Nickename() {
        return author_Nickename;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getHtml() {
        return html;
    }

    public void setAuthor_Nickename(String author_Nickename) {
        this.author_Nickename = author_Nickename;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
