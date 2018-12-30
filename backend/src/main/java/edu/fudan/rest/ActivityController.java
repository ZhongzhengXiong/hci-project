package edu.fudan.rest;


import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.*;
import edu.fudan.dto.response.*;
import edu.fudan.model.ActivityPhotoService;
import edu.fudan.model.ActivityService;
import edu.fudan.model.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final ReviewService reviewService;
    private final ActivityPhotoService activityPhotoService;

    @Autowired
    public ActivityController(ActivityService activityService,
                              ReviewService reviewService,
                              ActivityPhotoService activityPhotoService) {
        this.activityService = activityService;
        this.reviewService = reviewService;
        this.activityPhotoService = activityPhotoService;
    }

    @GetMapping
    @Authorization
    ResponseEntity<List<ActivityMetaResp>> getAllActivities() {
        return new ResponseEntity<>(activityService.getAllActivities(), HttpStatus.OK);
    }

    @PostMapping
    @Authorization
    ResponseEntity<ActivityPrivateResp> createActivity(@CurrentUser User currentUser,
                                                       @Valid @RequestBody CreateorUpdateActivityReq createActivityReq) {
        return new ResponseEntity<>(
                activityService.createActivity(currentUser, createActivityReq), HttpStatus.OK);

    }


    @PatchMapping("/{aid}")
    @Authorization
    ResponseEntity<ActivityStatusResp> updateActivityStatus(@CurrentUser User currentUser, @PathVariable long aid,
                                                  @RequestBody UpdateActivityStatusReq updateActivityStatusReq) {

        return new ResponseEntity<>(activityService.updateActivityStatus(currentUser, aid,
                updateActivityStatusReq.getStatus()), HttpStatus.NO_CONTENT);
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
                                                  @RequestBody CreateorUpdateActivityReq updateActivityReq) {
        return new ResponseEntity<>(activityService.updateActivity(currentUser, aid,
                updateActivityReq), HttpStatus.OK);
    }


    @GetMapping("/{aid}/users")
    @Authorization
    ResponseEntity<List<UserPublicResp>> getUsersOfActivity(@CurrentUser User currentUser,
                                                            @PathVariable long aid) {
        return new ResponseEntity<>(activityService.getAllUsersOfActivity(currentUser, aid), HttpStatus.OK);
    }

    @PostMapping("/{cid}/users")
    @Authorization
    ResponseEntity<ActivityPrivateResp> addUserToActivity(@CurrentUser User currentUser,
                                                         @PathVariable long aid,
                                                         @Valid @RequestBody JoinActivityReq joinActivityReq) {
        return new ResponseEntity<>(activityService.addUserToActivity(
                currentUser, aid, joinActivityReq.getInvitingCode()), HttpStatus.CREATED);
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


}
