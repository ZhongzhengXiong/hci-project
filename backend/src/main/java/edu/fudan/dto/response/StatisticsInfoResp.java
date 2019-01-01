package edu.fudan.dto.response;


import edu.fudan.domain.ActivityType;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Set;

public class StatisticsInfoResp {

    @NotNull
    private int participateNum;

    @NotNull
    private int createNum;


    @NotNull
    private HashMap<ActivityType, Integer> activityTypeStats;


    @NotNull
    private Set<String> placeSet;

    @NotNull
    private int reviewNum;

    @NotNull

    private int uploadPhotoNum;

    public void setReviewNum(int reviewNum) {
        this.reviewNum = reviewNum;
    }

    public void setUploadPhotoNum(int uploadPhotoNum) {
        this.uploadPhotoNum = uploadPhotoNum;
    }

    public StatisticsInfoResp() {
    }

    public void setParticipateNum(int participateNum) {
        this.participateNum = participateNum;
    }

    public void setCreateNum(int createNum) {
        this.createNum = createNum;
    }

    public void setActivityTypeStats(HashMap<ActivityType, Integer> activityTypeStats) {
        this.activityTypeStats = activityTypeStats;
    }

    public void setPlaceSet(Set<String> placeSet) {
        this.placeSet = placeSet;
    }

    public int getParticipateNum() {
        return participateNum;
    }

    public int getCreateNum() {
        return createNum;
    }

    public HashMap<ActivityType, Integer> getActivityTypeStats() {
        return activityTypeStats;
    }

    public Set<String> getPlaceSet() {
        return placeSet;
    }

    public int getReviewNum() {
        return reviewNum;
    }

    public int getUploadPhotoNum() {
        return uploadPhotoNum;
    }
}
