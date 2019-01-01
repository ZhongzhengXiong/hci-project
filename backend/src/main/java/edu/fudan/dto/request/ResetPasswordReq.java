package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static edu.fudan.Application.PASSWORD_REGEX;

public class ResetPasswordReq {

    @NotBlank
    private String oldPassword;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String newPassword;


    public ResetPasswordReq() {
    }

    public ResetPasswordReq(@Pattern(regexp = PASSWORD_REGEX, message = "invalid password.") @NotBlank String oldPassword, @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.") @NotBlank String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
