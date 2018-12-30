package edu.fudan.dto.response;

import edu.fudan.domain.User;

public class UserPublicResp {
    private String name;

    private long id;


    // todo headPortraitUri
    private long headPortraitUri;


    public UserPublicResp() {
    }

    public UserPublicResp(User user) {
        this.name = user.getName();
        this.id = user.getUserId();

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getHeadPortraitUri(){
        return  headPortraitUri;
    }

}
