package com.cbt.main.model;

/**
 * Created by vigorous on 18/4/2.
 */

public class ReplyModel {
    private String commentname;
    private String content;
    private String replayuserid;
    private String replayusername;

    public void setCommentname(String commentname) {
        this.commentname = commentname;
    }

    public String getCommentname() {
        return commentname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setReplayuserid(String replayuserid) {
        this.replayuserid = replayuserid;
    }

    public String getReplayuserid() {
        return replayuserid;
    }

    public void setReplayusername(String replayusername) {
        this.replayusername = replayusername;
    }

    public String getReplayusername() {
        return replayusername;
    }
}
