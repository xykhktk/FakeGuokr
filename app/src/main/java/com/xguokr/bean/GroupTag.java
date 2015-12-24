package com.xguokr.bean;

import java.io.Serializable;

/**
 * Created by yk on 2015/12/8.
 */
public class GroupTag implements Serializable{
    private String groupName;
    private String groupImageUrl;
    private String groupMemberNum;
    private String groupId;
    private String rank_num_top;

    public String getRank_num_top() {
        return rank_num_top;
    }

    public void setRank_num_top(String rank_num_top) {
        this.rank_num_top = rank_num_top;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTagImageUrl() {
        return groupImageUrl;
    }

    public String getGroupMemberNum() {
        return groupMemberNum;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        this.groupImageUrl = groupImageUrl;
    }

    public void setGroupMemberNum(String groupMemberNum) {
        this.groupMemberNum = groupMemberNum;
    }
}
