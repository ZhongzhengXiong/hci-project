package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.fudan.domain.ActivityPhoto;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

public class ActivityPhotoResp {

    @NotNull
    private long photoId;

    @NotNull
    private String activityName;

    @NotNull
    private long activityId;

    @NotNull
    private UserPublicResp uploader;

//    @NotNull
//    private long userId;
//
//    @NotNull
//    private String userName;

    @NotNull
    private String photoName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
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

//

    public String getPhotoName() {
        return photoName;
    }

    public ActivityPhotoResp(ActivityPhoto activityPhoto){
        this.photoId = activityPhoto.getPhotoId();
        this.activityId = activityPhoto.getActivity().getActivityId();
        this.activityName= activityPhoto.getActivity().getName();
//        this.userId = activityPhoto.getUser().getUserId();
//        this.userName = activityPhoto.getUser().getName();
        this.date = activityPhoto.getDate();
        this.photoName = activityPhoto.getPhotoName();
        this.uploader = new UserPublicResp(activityPhoto.getUser());

    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public UserPublicResp getUploader() {
        return uploader;
    }

    public void setUploader(UserPublicResp uploader) {
        this.uploader = uploader;
    }

    //    public long getUserId() {
//        return userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
}
