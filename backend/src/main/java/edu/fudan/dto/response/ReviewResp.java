package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.fudan.domain.Review;
import edu.fudan.domain.User;
import edu.fudan.repository.UserRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReviewResp {

    @NotBlank
    private long reviewId;


//    @NotBlank
//    private long userId;

    @NotNull
    private UserPublicResp creator;

    @NotNull
    private String content;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date date;

    public ReviewResp() {
    }

    public long getReviewId() {
        return reviewId;
    }

//    public long getUserId() {
//        return userId;
//    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ReviewResp(@NotBlank long reviewId, @NotNull User user, @NotNull String content, @NotNull Date date) {
        this.reviewId = reviewId;
        this.creator = new UserPublicResp(user);
        this.content = content;
        this.date = date;
    }

//    public ReviewResp(Review review) {
//        this.reviewId = review.getReviewId();
//        this.creator = review.getUserId();
//        this.content = review.getContent();
//        this.date = review.getDate();
//    }


    public UserPublicResp getCreator() {
        return creator;
    }

    public void setCreator(UserPublicResp creator) {
        this.creator = creator;
    }
}
