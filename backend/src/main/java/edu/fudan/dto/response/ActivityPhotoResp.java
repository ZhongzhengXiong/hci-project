package edu.fudan.dto.response;

import edu.fudan.domain.ActivityPhoto;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

public class ActivityPhotoResp {

    @NotNull
    private long photoId;

    @NotNull
    private String photoUri;

    @NotNull
    private String activityName;


    @NotNull
    private long activityId;

    @NotNull
    private Date date;

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public String getActivityName() {
        return activityName;
    }

    public long getActivityId() {
        return activityId;
    }

    public Date getDate() {
        return date;
    }

    public ActivityPhotoResp(){

    }


    public ActivityPhotoResp(@NotNull long photoId, @NotNull String photoUri,
                             @NotNull long activityId, @NotNull Date date) {
        this.photoId = photoId;
        this.photoUri = photoUri;
        this.activityId = activityId;
        this.date = date;
    }

    public ActivityPhotoResp(ActivityPhoto activityPhoto){
        this.photoId = activityPhoto.getPhotoId();
        this.activityId = activityPhoto.getActivityId();
        this.date = activityPhoto.getDate();
        //todo
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
