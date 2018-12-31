package edu.fudan.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;

@NodeEntity
public class ActivityPhoto {

    @Id
    private Long photoId;

    @Property
    private Long activityId;

    @Property
    private Long userId;

    @Property
    @DateString
    private Date date;

    public ActivityPhoto(){

    }

    public ActivityPhoto(Long photoId, Long activityId, Long userId) {
        this.photoId = photoId;
        this.activityId = activityId;
        this.userId = userId;
        this.date = new Date();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getDate() {
        return date;
    }
}
