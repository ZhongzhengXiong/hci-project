package edu.fudan.dto.response;

import edu.fudan.domain.Activity;
import edu.fudan.domain.ActivityType;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class ActivityMetaResp {
    @NotBlank
    private Long activityId;

    @NotBlank
    private String place;

    @NotBlank
    private String name;

    @NotBlank
    @DateString
    private Date beginDate;

    @NotBlank
    @DateString
    private Date endDate;

    @NotBlank
    private boolean status;

    @NotBlank
    private String photoLink;

    public ActivityMetaResp() {
    }

    public ActivityMetaResp(Activity activity, String photoLink) {
        this.activityId = activity.getActivityId();
        this.place = activity.getPlace();
        this.name = activity.getName();
        this.beginDate = activity.getBeginDate();
        this.endDate = activity.getEndDate();
        this.status = activity.getStatus();
        this.photoLink = photoLink;
        //todo photoLink
    }


    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
