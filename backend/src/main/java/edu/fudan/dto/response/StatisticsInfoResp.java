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


    private ActivityType mostFrequentType;


    private String mostFrequentPlace;


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

    public ActivityType getMostFrequentType() {
        return mostFrequentType;
    }

    public String getMostFrequentPlace() {
        return mostFrequentPlace;
    }

    public void setParticipateNum(int participateNum) {
        this.participateNum = participateNum;
    }

    public void setCreateNum(int createNum) {
        this.createNum = createNum;
    }

    public void setMostFrequentType(ActivityType mostFrequentType) {
        this.mostFrequentType = mostFrequentType;
    }

    public void setMostFrequentPlace(String mostFrequentPlace) {
        this.mostFrequentPlace = mostFrequentPlace;
    }




    public int getParticipateNum() {
        return participateNum;
    }

    public int getCreateNum() {
        return createNum;
    }

//    public HashMap<ActivityType, Integer> getActivityTypeStats() {
//        return activityTypeStats;
//    }
//
//    public Set<String> getPlaceSet() {
//        return placeSet;
//    }

    public int getReviewNum() {
        return reviewNum;
    }

    public int getUploadPhotoNum() {
        return uploadPhotoNum;
    }
}
