package edu.fudan.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;

@NodeEntity
public class Review {

    @Id
    private Long reviewId;

    @Property
    private String content;

    @Property
    private Long userId;

    @Property
    @DateString
    private Date date;


    public Review(Long reviewId, String content, Long userId) {
        this.reviewId = reviewId;
        this.content = content;
        this.userId = userId;
        this.date = new Date();
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }
}
