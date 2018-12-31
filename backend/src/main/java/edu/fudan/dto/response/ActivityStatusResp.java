package edu.fudan.dto.response;

import javax.validation.constraints.NotNull;

public class ActivityStatusResp {
    @NotNull
    private long activityId;

    @NotNull
    private boolean status;


    public ActivityStatusResp(@NotNull long activityId, @NotNull boolean status) {
        this.activityId = activityId;
        this.status = status;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getActivityId() {
        return activityId;
    }

    public boolean getStatus() {
        return status;
    }

    public ActivityStatusResp() {
    }
}
