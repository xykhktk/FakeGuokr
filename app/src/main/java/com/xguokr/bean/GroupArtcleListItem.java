package com.xguokr.bean;

import java.io.Serializable;

/**
 * Created by yk on 2015/12/11.
 */
public class GroupArtcleListItem implements Serializable{

    private String title;
    private String authorNickename;
    private String date_created;
    private String html;
    private String content;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorNickename(String authorNickename) {
        this.authorNickename = authorNickename;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorNickename() {
        return authorNickename;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getHtml() {
        return html;
    }

    public String getContent() {
        return content;
    }
}
