package edu.fudan.dto.request;

import javax.validation.constraints.NotNull;

public class CreateNoticeReq {

    @NotNull
    private String title;

    @NotNull
    private String content;

    public CreateNoticeReq(@NotNull String title, @NotNull String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
