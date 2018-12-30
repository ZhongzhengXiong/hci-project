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

    @NotBlank
    private boolean status;


    public ActivityPublicResp(Activity activity, String photoLink) {
        super(activity, photoLink);
        this.detail = activity.getDetail();
        this.userLimit = activity.getUserLimit();
        this.participatorIds = new ArrayList<>();
        for(User participator: activity.getParticipators()){
            this.participatorIds.add(participator.getUserId());
        }
        this.status = activity.getStatus();
        this.creatorId = activity.getCreator().getUserId();
    }
}
