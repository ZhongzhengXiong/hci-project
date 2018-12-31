package edu.fudan.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;

@NodeEntity
public class Notice {
    @Id
    private Long noticeId;

    @Property
    private String content;

    @Property
    private String title;

    @Property
    @DateString
    private Date date;

    public Notice(){

    }

    public Notice(Long noticeId, String content, String title) {
        this.noticeId = noticeId;
        this.content = content;
        this.title = title;
        this.date = new Date();
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
