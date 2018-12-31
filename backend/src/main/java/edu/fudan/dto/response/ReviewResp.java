package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.fudan.domain.Review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReviewResp {

    @NotBlank
    private long reviewId;


    @NotBlank
    private long userId;


    @NotNull
    private String content;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    public ReviewResp(@NotBlank long reviewId, @NotBlank long userId, @NotNull String content, @NotNull Date date) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.content = content;
        this.date = date;
    }

    public ReviewResp(Review review) {
        this.reviewId = review.getReviewId();
        this.userId = review.getReviewId();
        this.content = review.getContent();
        this.date = review.getDate();
    }
}
