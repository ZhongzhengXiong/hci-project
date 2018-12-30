package edu.fudan.dto.request;

import edu.fudan.Application;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginReq {

//    @Email(message = "invalid email.")
    @Pattern(regexp = Application.PHONE_REGEX, message = "invalid phone number")
    @NotBlank
    private String phone;

    @Pattern(regexp = Application.PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String password;

    public LoginReq() {
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
