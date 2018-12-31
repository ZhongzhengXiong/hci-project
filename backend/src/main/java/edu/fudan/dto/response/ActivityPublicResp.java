package edu.fudan.dto.response;

import edu.fudan.domain.Activity;
import edu.fudan.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class ActivityPublicResp extends ActivityMetaResp{

    @NotBlank
    private String detail;

    @NotBlank
    private int userLimit;

    @NotBlank
    private List<Long>  participatorIds;


    @NotBlank
    private long creatorId;

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public void setParticipatorIds(List<Long> participatorIds) {
        this.participatorIds = participatorIds;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public ActivityPublicResp(){

    }


    public ActivityPublicResp(Activity activity) {
        super(activity);
        this.detail = activity.getDetail();
        this.userLimit = activity.getUserLimit();
        this.participatorIds = new ArrayList<>();
        for(User participator: activity.getParticipators()){
            this.participatorIds.add(participator.getUserId());
        }
        this.creatorId = activity.getCreator().getUserId();
    }
}
