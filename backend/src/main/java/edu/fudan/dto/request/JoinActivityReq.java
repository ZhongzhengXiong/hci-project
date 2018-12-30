package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;

public class JoinActivityReq {
    @NotBlank
    private String invitingCode;

    public JoinActivityReq() {
    }

    public String getInvitingCode() {
        return invitingCode;
    }
}
