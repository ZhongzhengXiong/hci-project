package edu.fudan.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;

@NodeEntity
public class ActivityPhoto {

    @Id
    private Long photoId;

    @Property
    private String photoName;

    @Property
    @DateString
    private Date date;

    @Relationship(type = "USER_HAS_PHOTO", direction = Relationship.INCOMING)
    private User user;

    @Relationship(type = "ACTIVITY_HAS_PHOTO", direction = Relationship.INCOMING)
    private Activity activity;


    public ActivityPhoto(){

    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoName() {

        return photoName;
    }

    public ActivityPhoto(Long photoId, Activity activity, User user, String photoName) {
        this.photoId = photoId;
        this.activity = activity;
        this.user = user;
        this.date = new Date();
        this.photoName = photoName;

    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Long getPhotoId() {
        return photoId;
    }


    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public User getUser() {
        return user;
    }

    public Activity getActivity() {
        return activity;
    }

    public Date getDate() {
        return date;
    }
}
