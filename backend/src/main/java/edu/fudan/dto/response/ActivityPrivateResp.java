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


    @NotBlank
    private long creatorId;


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
        this.creatorId = activity.getCreator().getUserId();
        this.invitingCode = activity.getInvitingCode();
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

    public long getCreatorId() {
        return creatorId;
    }


    public ActivityPrivateResp() {

    }
}
