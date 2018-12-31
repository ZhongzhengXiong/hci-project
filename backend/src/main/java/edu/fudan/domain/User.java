package edu.fudan.domain;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class User {
    @Id
    private Long userId;

    @Property
    private String name;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    @Index(unique = true)
    private String phone;

    @Property
    private String avatar;


    @Relationship(type = "CREATE_ACTIVITY", direction = "OUTGOING")
    private List<Activity> createdActivities;
    
    
    @Relationship(type = "OWN_PARTICIPATOR", direction = Relationship.INCOMING)
    private List<Activity>  participatedActivies;

    @Relationship(type = "HAS_MESSAGE", direction = "OUTGOING")
    private List<Message> messageList;


    @Relationship(type = "USER_HAS_PHOTO", direction = Relationship.OUTGOING)
    private List<ActivityPhoto> activityPhotos;


    public List<Activity> getCreatedActivities() {
        return createdActivities == null ? new ArrayList<>() : createdActivities;

    }

    public List<Activity> getParticipatedActivies() {
        return participatedActivies == null? new ArrayList<>(): participatedActivies;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(){

    }

    public User(Long userId, String name, String password, String phone, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.createdActivities = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.participatedActivies = new ArrayList<>();
        this.activityPhotos = new ArrayList<>();
        this.avatar = "";
    }

    public List<ActivityPhoto> getActivityPhotos() {
        return activityPhotos ==  null ? new ArrayList<>() : activityPhotos;
    }

    public User(Long userId, String name, String password, String phone, String email, String avatar){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.createdActivities = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.participatedActivies = new ArrayList<>();
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {

        return email;
    }

    public void addActivity(Activity activity){
        this.createdActivities.add(activity);
    }

    public void addMessage(Message message){
        this.messageList.add(message);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Message> getMessageList(){
        return messageList == null ? new ArrayList<>() : messageList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
