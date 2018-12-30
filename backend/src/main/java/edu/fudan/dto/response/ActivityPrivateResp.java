package edu.fudan.dto.response;

import edu.fudan.domain.Activity;

import javax.validation.constraints.NotNull;

public class ActivityPrivateResp extends ActivityPublicResp{
    @NotNull
    private String invitingCode;

    public ActivityPrivateResp(Activity activity, String photoLink) {
        super(activity, photoLink);
        this.invitingCode = activity.getInvitingCode();
    }
}
