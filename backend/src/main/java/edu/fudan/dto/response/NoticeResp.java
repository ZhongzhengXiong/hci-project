package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.fudan.domain.Notice;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class NoticeResp {
    @NotNull
    private Long noticeId;

    @NotNull
    private String content;

    @NotNull
    private String title;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    public NoticeResp(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.content = notice.getContent();
        this.title = notice.getTitle();
        this.date = notice.getDate();
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
