package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.fudan.domain.Message;

import java.util.Date;

public class MessageResp {

    private long messageId;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date date;

    private String activityName;

    private long activityId;

    public MessageResp() {
    }

    public MessageResp(Message message) {
        this.messageId = message.getMessageId();
        this.content = message.getContent();
        this.date = message.getDate();
        this.activityName = message.getActivity().getName();
        this.activityId = message.getActivity().getActivityId();
    }


    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {

        return activityName;
    }

    public long getActivityId() {
        return activityId;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public long getMessageId() {

        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
