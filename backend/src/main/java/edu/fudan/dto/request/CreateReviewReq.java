package edu.fudan.dto.request;

import javax.validation.constraints.NotNull;

public class CreateReviewReq {

    @NotNull
    private String content;

    public CreateReviewReq(@NotNull String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public CreateReviewReq() {
    }


}
