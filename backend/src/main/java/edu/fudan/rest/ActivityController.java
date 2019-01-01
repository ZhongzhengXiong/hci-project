package edu.fudan.rest;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.Activity;
import edu.fudan.domain.ActivityPhoto;
import edu.fudan.domain.User;
import edu.fudan.dto.request.*;
import edu.fudan.dto.response.*;
import edu.fudan.exception.ActivityNotFoundException;
import edu.fudan.exception.ActivityPhotoNotFoundException;
import edu.fudan.exception.ParseJsonStringException;
import edu.fudan.model.ActivityPhotoService;
import edu.fudan.model.ActivityService;
import edu.fudan.model.ReviewService;
import edu.fudan.repository.ActivityRepository;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ReviewService reviewService;
    private final ActivityPhotoService activityPhotoService;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityService activityService,
                              ReviewService reviewService,
                              ActivityPhotoService activityPhotoService,
                              ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.reviewService = reviewService;
        this.activityPhotoService = activityPhotoService;
        this.activityRepository = activityRepository;
    }

//    @GetMapping
//    ResponseEntity<List<ActivityMetaResp>> getAllActivities() {
//        return new ResponseEntity<>(activityService.getAllActivities(), HttpStatus.OK);
//    }

    @GetMapping
    ResponseEntity<ActivityMetaResp> getActivity(@RequestParam("invitingcode") String invitingCode){
        return new ResponseEntity<ActivityMetaResp>(activityService.getActivityByInvitingCode(invitingCode), HttpStatus.OK);
    }


    @PostMapping
    @Authorization
    ResponseEntity<ActivityPrivateResp> createActivity(@CurrentUser User currentUser,
                                                       @RequestParam("introPhoto") MultipartFile introPhoto,
                                                       @RequestParam("createActivityReq") String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        CreateorUpdateActivityReq createActivityReq = null;
        try {
            createActivityReq = mapper.readValue(jsonString, CreateorUpdateActivityReq.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParseJsonStringException();
        }

        return new ResponseEntity<>(
                activityService.createActivity(currentUser, createActivityReq, introPhoto), HttpStatus.OK);

    }


    @PatchMapping("/{aid}")
    @Authorization
    ResponseEntity<ActivityStatusResp> updateActivityStatus(@CurrentUser User currentUser, @PathVariable long aid,
                                                            @RequestBody UpdateActivityStatusReq updateActivityStatusReq) {

        return new ResponseEntity<>(activityService.updateActivityStatus(currentUser, aid,
                updateActivityStatusReq.getStatus()), HttpStatus.OK);
    }

    @GetMapping("/{aid}")
    @Authorization
    ResponseEntity<ActivityMetaResp> getActivity(@CurrentUser User currentUser,
                                                 @PathVariable long aid) {
        return new ResponseEntity<ActivityMetaResp>(activityService.getActivity(currentUser, aid), HttpStatus.OK);
    }

    @PutMapping("/{aid}")
    @Authorization
    ResponseEntity<ActivityPrivateResp> updateActivity(@CurrentUser User currentUser,
                                                       @PathVariable long aid,
                                                       @RequestParam("introPhoto") MultipartFile file,
                                                       @RequestParam("updateActivityReq") String jsonString) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        CreateorUpdateActivityReq updateActivityReq = null;
        try {
            updateActivityReq = mapper.readValue(jsonString, CreateorUpdateActivityReq.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParseJsonStringException();
        }
        return new ResponseEntity<>(activityService.updateActivity(currentUser, aid,
                updateActivityReq, file), HttpStatus.OK);
    }


    @GetMapping("/{aid}/users")
    @Authorization
    ResponseEntity<List<UserPublicResp>> getUsersOfActivity(@CurrentUser User currentUser,
                                                            @PathVariable("aid") long aid) {
        return new ResponseEntity<>(activityService.getAllUsersOfActivity(currentUser, aid), HttpStatus.OK);
    }

//    @PostMapping("/{aid}/users")
//    @Authorization
//    ResponseEntity<ActivityPrivateResp> addUserToActivity(@CurrentUser User currentUser,
//                                                          @PathVariable("aid") long aid,
//                                                          @Valid @RequestBody JoinActivityReq joinActivityReq) {
//        return new ResponseEntity<>(activityService.addUserToActivity(
//                currentUser, aid, joinActivityReq.getInvitingCode()), HttpStatus.CREATED);
//    }

    @PostMapping("/{aid}/users")
    @Authorization
    ResponseEntity<ActivityPrivateResp> addUserToActivity(@CurrentUser User currentUser,
                                                          @PathVariable("aid") long aid){
        return new ResponseEntity<>(activityService.addUserToActivity(currentUser, aid), HttpStatus.OK);
    }




    @GetMapping("/{aid}/notices")
    @Authorization
    ResponseEntity<List<NoticeResp>> getNoticesOfActivity(@PathVariable long aid,
                                                          @CurrentUser User currentUser) {
        return new ResponseEntity<>(activityService.getAllNoticesOfActivity(currentUser, aid), HttpStatus.OK);
    }

    @PostMapping("{aid}/notices")
    @Authorization
    ResponseEntity<NoticeResp> addNoticesToActivity(@CurrentUser User currentUser,
                                                    @PathVariable long aid,
                                                    @Valid @RequestBody CreateNoticeReq createNoticeReq) {
        return new ResponseEntity<NoticeResp>(activityService.createNotice(currentUser, aid, createNoticeReq), HttpStatus.CREATED);
    }

    @GetMapping("/{aid}/reviews")
    @Authorization
    ResponseEntity<List<ReviewResp>> getReviewsOfActivity(@PathVariable long aid,
                                                          @CurrentUser User currentUser) {
        return new ResponseEntity<>(activityService.getAllReviewsOfActivity(currentUser, aid), HttpStatus.OK);
    }

    @PostMapping("{aid}/reviews")
    @Authorization
    ResponseEntity<ReviewResp> addReviewsToActivity(@CurrentUser User currentUser,
                                                    @PathVariable long aid,
                                                    @Valid @RequestBody CreateReviewReq createReviewReq) {
        return new ResponseEntity<>(activityService.createReview(currentUser, aid, createReviewReq), HttpStatus.CREATED);
    }


    @GetMapping("{aid}/photos")
    @Authorization
    ResponseEntity<List<ActivityPhotoResp>> getPhotosOfActivity(@CurrentUser User currentUser,
                                                    @PathVariable long aid) {
        return new ResponseEntity<>(activityService.getAllPhotosOfActivity(currentUser, aid), HttpStatus.CREATED);
    }

    @PostMapping("{aid}/photos")
    @Authorization
    ResponseEntity<ActivityPhotoResp> addPhotosToActivity(@CurrentUser User currentUser,
                                                    @PathVariable long aid,
                                                          @RequestParam("file")MultipartFile file) {
        return new ResponseEntity<>(activityService.addPhotoToActivity(currentUser, aid, file), HttpStatus.CREATED);
    }

    @GetMapping("{aid}/intro-photo")
    ResponseEntity<InputStreamResource> getIntroPhotoOfActivity(@PathVariable long aid){
        Activity activity = activityRepository.findById(aid).orElseThrow(
                () -> new ActivityNotFoundException(aid)
        );
        // Set header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + activity.getIntroPhotoName() + "\"");
        return new ResponseEntity<>(activityService.getIntroPhoto(aid), headers, HttpStatus.OK);

    }

}
