package edu.fudan.dto.request;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static edu.fudan.Application.PASSWORD_REGEX;
import static edu.fudan.Application.PHONE_REGEX;

public class RegisterReq {

    @Pattern(regexp = PHONE_REGEX, message = "invalid phone number")
    @NotBlank
    private String phone;

    @NotBlank(message = "name can't be empty")
    private String name;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;



//    @NotNull
//    private String verificationCode;

    public RegisterReq() {
    }

//    public String getEmail() {
//        return email;
//    }

    public String getPhone(){return phone;}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    //    public String getVerificationCode() { return verificationCode; }
}
