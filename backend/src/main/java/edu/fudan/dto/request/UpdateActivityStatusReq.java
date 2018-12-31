package edu.fudan.dto.request;

import javax.validation.constraints.NotNull;

public class UpdateActivityStatusReq {
    @NotNull
    private boolean status;

    public UpdateActivityStatusReq(@NotNull boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public UpdateActivityStatusReq() {
    }
}
