package edu.fudan.dto.response;

import edu.fudan.domain.User;

public class UserPrivateResp extends UserPublicResp {

    /**
     * Email is not public data.
     * Thus only authorized request could get not-null email.
     */
    private String email;

    private String phone;


    public UserPrivateResp() {
    }

    public UserPrivateResp(User user) {
        super(user);
        this.phone = user.getPhone();
        this.email = user.getEmail();
    }

    public String getPhone(){
        return phone;
    }
    public String getEmail() {
        return email;
    }
}
