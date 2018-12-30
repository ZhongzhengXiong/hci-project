package edu.fudan.dto.response;

import edu.fudan.domain.Message;

import java.util.Date;

public class MessageResp {

    private long messageId;

    private String title;

    private String content;

    private Date date;

    public MessageResp() {
    }

    public MessageResp(Message message) {
        this.messageId = message.getMessageId();
        this.title = message.getTitle();
        this.content = message.getContent();
        this.date = message.getDate();
    }


    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
