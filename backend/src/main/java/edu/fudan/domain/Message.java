package edu.fudan.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NodeEntity
public class Message {
    @Id
    private Long messageId;

    @Property
    @DateString
    private Date date;

    @Property
    private String content;

//    @Property
//    private String title;


    @Property
    private boolean status;

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.INCOMING)
    private List<User> users;

    @Relationship(type = "BELONG_TO_ACTIIVTY", direction = Relationship.OUTGOING)
    private Activity activity;


    public Message(Long messageId, Date date, String content, Activity activity) {
        this.messageId = messageId;
        this.date = date;
        this.content = content;
        this.status = false;
        this.users = new ArrayList<>();
        this.activity = activity;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public List<User> getUsers() {
        return users == null ? new ArrayList<>() : users;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
//    public User getUser() {
//        return user;
//    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public Message() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public MessageType getType() {
//        return type;
//    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setType(MessageType type) {
//        this.type = type;
//    }
}
