package edu.fudan.rest;

import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.UpdateUserReq;
import edu.fudan.dto.response.*;
import edu.fudan.model.ActivityPhotoService;
import edu.fudan.model.ActivityService;
import edu.fudan.model.MessagesService;
import edu.fudan.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    private final ActivityService activityService;

    private final MessagesService messagesService;

    private final ActivityPhotoService activityPhotoService;

    @Autowired
    public AccountController(UserService userService, ActivityService activityService,
                             MessagesService messagesService, ActivityPhotoService activityPhotoService) {
        this.userService = userService;
        this.activityService = activityService;
        this.messagesService = messagesService;
        this.activityPhotoService = activityPhotoService;
    }

    /**
     * Get user private data.
     *
     * @param currentUser, current createToken user
     * @return user private data
     */
    @GetMapping
    @Authorization
    ResponseEntity<UserPrivateResp> getUserPrivate(@CurrentUser User currentUser) {
        UserPrivateResp userPrivateResp = new UserPrivateResp(currentUser);

        return new ResponseEntity<>(userPrivateResp, HttpStatus.OK);
    }

    /**
     * Update user profile.
     *
     * @param currentUser,   current createToken user
     * @param updateUserReq, data fields to update
     * @return updated user private data
     */
    @PutMapping
    @Authorization
    ResponseEntity updateUser(@CurrentUser User currentUser, @RequestBody UpdateUserReq updateUserReq) {
        userService.updateUser(currentUser, updateUserReq.getName(), updateUserReq.getEmail(),
                updateUserReq.getPassword(), updateUserReq.getNewPassword());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Delete the user with {uid}
     *
     * @param currentUser, current createToken user
     * @return empty response body
     */
    @DeleteMapping
    @Authorization
    ResponseEntity deleteUser(@CurrentUser User currentUser) {
        userService.deleteUser(currentUser);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/activities")
    @Authorization
    ResponseEntity<List<ActivityMetaResp>> getActivitiesOfUser(@CurrentUser User currentUser) {
        return new ResponseEntity<List<ActivityMetaResp>>(activityService.getAllActivitiesOfUser(currentUser), HttpStatus.OK);
    }


    @GetMapping("/messages")
    @Authorization
    ResponseEntity<List<MessageResp>> getMessagesOfUser(@CurrentUser User currentUser) {
        return new ResponseEntity<List<MessageResp>>(messagesService.getAllMessagesOfUser(currentUser), HttpStatus.OK);
    }

    @GetMapping("/photos")
    @Authorization
    ResponseEntity<List<ActivityPhotoResp>> getPhotosOfUser(@CurrentUser User currentUser) {
        return new ResponseEntity<List<ActivityPhotoResp>>(activityPhotoService.getAllPhotosfUser(currentUser), HttpStatus.OK);
    }


    @GetMapping("/stat-info")
    @Authorization
    ResponseEntity<StatisticsInfoResp> getStatisticsOfUser(@CurrentUser User currentUser){
        return new ResponseEntity<StatisticsInfoResp>(activityService.getStatisticsOfUser(currentUser), HttpStatus.OK);
    }

}
