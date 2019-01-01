package edu.fudan.dto.response;

import edu.fudan.domain.Activity;
import edu.fudan.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ActivityPrivateResp extends ActivityMetaResp {
    @NotBlank
    private String detail;

    @NotBlank
    private int userLimit;

    @NotBlank
    private List<Long> participatorIds;


//    public String getCreatorAvatar() {
//        return creatorAvatar;
//    }

    @NotNull
    private UserPublicResp creator;

//    @NotBlank
//    private long creatorId;
//
//    @NotNull
//    private String creatorAvatar;

//    public void setCreatorAvatar(String creatorAvatar) {
//        this.creatorAvatar = creatorAvatar;
//    }

    @NotNull
    private String invitingCode;

    public void setInvitingCode(String invitingCode) {
        this.invitingCode = invitingCode;
    }

    public ActivityPrivateResp(Activity activity) {
        super(activity);
        this.detail = activity.getDetail();
        this.userLimit = activity.getUserLimit();
        this.participatorIds = new ArrayList<>();
        for (User participator : activity.getParticipators()) {
            this.participatorIds.add(participator.getUserId());
        }
//        this.creatorId = activity.getCreator().getUserId();
        this.invitingCode = activity.getInvitingCode();
//        this.creatorAvatar = activity.getCreator().getAvatar();
        this.creator = new UserPublicResp(activity.getCreator());
    }

    public String getInvitingCode() {
        return invitingCode;
    }

    public String getDetail() {
        return detail;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public List<Long> getParticipatorIds() {
        return participatorIds;
    }

//    public long getCreatorId() {
//        return creatorId;
//    }


    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public void setParticipatorIds(List<Long> participatorIds) {
        this.participatorIds = participatorIds;
    }

    public void setCreator(UserPublicResp creator) {
        this.creator = creator;
    }

    public UserPublicResp getCreator() {
        return creator;
    }

    public ActivityPrivateResp() {

    }
}
