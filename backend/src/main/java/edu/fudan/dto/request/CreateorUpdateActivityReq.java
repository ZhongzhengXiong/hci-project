package edu.fudan.dto.request;

import edu.fudan.domain.ActivityType;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateorUpdateActivityReq {

    //todo photo uploading

    @NotBlank
    private String place;

    @NotBlank
    private String name;

    @NotBlank
    private String detail;


    @NotNull
    private Date beginDate;

    @NotNull
    private Date endDate;


    @NotBlank
    private int userLimit;

    @NotNull
    private ActivityType type;


    public CreateorUpdateActivityReq() {
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public ActivityType getType() {
        return type;
    }
}
