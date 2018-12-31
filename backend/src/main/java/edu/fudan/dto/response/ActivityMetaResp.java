package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date beginDate;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;

    @NotBlank
    private boolean status;

    @NotBlank
    private String introPhotoName;

    public ActivityMetaResp() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setIntroPhotoName(String introPhotoName) {
        this.introPhotoName = introPhotoName;
    }

    public String getPlace() {

        return place;
    }

    public String getName() {
        return name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isStatus() {
        return status;
    }

    public String getIntroPhotoName() {
        return introPhotoName;
    }

    public ActivityMetaResp(Activity activity) {
        this.activityId = activity.getActivityId();
        this.place = activity.getPlace();
        this.name = activity.getName();
        this.beginDate = activity.getBeginDate();
        this.endDate = activity.getEndDate();
        this.status = activity.getStatus();
        this.introPhotoName = activity.getIntroPhotoName();
    }


}
