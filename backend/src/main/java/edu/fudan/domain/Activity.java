package edu.fudan.domain;


import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NodeEntity
public class Activity {
    @Id
    private Long activityId;

    @Property
    @Index(unique = true)
    private String invitingCode;

    @Property
    private String place;

    @Property
    private String name;

    @Property
    private String detail;


    @Property
    @DateString
    private Date beginDate;

    @Property
    @DateString
    private Date endDate;


    @Property
    private int userLimit;

    @Property
    private boolean status;

    @Property
    private ActivityType activityType;


    @Relationship(type = "OWN_PARTICIPATOR", direction = Relationship.OUTGOING)
    private List<User> participators;


    @Relationship(type = "CREATE_ACTIVITY", direction = Relationship.INCOMING)
    private User creator;



    @Relationship(type = "HAS_REVIEW")
    private List<Review> reviews;


    @Relationship(type = "HAS_NOTICE")
    private List<Notice> notices;


    public Activity(Long activityId, User user){
        this.activityId = activityId;
        this.participators = new ArrayList<>();
        this.creator = user;
        this.participators.add(user);
        this.reviews = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setInvitingCode(String invitingCode) {
        this.invitingCode = invitingCode;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addParticipator(User user){
        this.participators.add(user);
    }

    public void addReviews(Review review){
        this.reviews.add(review);

    }

    public void addNotice(Notice notice){
        this.notices.add(notice);
    }

    public User getCreator() {
        return creator;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getInvitingCode() {
        return invitingCode;
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public boolean getStatus() {
        return status;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public List<User> getParticipators() {
        return participators == null ? new ArrayList<>() : participators;
    }

    public List<Notice> getNotices() {return notices == null ? new ArrayList<>() : notices;}

    public List<Review> getReviews() {
        return reviews == null ? new ArrayList<>() : reviews;
    }


    //    public Activity(Long activityId, String invitingCode, String place, String name, String detail,
//                    Date beginDate, Date endDate, int userLimit, boolean status,
//                    ActivityType activityType, List<User> participators) {
//        this.activityId = activityId;
//        this.invitingCode = invitingCode;
//        this.place = place;
//        this.name = name;
//        this.detail = detail;
//        this.beginDate = beginDate;
//        this.endDate = endDate;
//        this.userLimit = userLimit;
//        this.status = status;
//        this.activityType = activityType;
//        this.participators = participators;
//    }
}
