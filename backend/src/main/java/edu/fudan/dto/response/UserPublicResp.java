package edu.fudan.dto.response;

import edu.fudan.domain.User;

public class UserPublicResp {
    private String name;

    private long id;

    private String avatar;


    public UserPublicResp() {
    }

    public UserPublicResp(User user) {
        this.name = user.getName();
        this.id = user.getUserId();
        this.avatar = user.getAvatar();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
