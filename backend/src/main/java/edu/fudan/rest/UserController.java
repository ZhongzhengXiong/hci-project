package edu.fudan.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.Application;
import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.MailRelatedReq;
import edu.fudan.dto.request.RegisterReq;
import edu.fudan.dto.response.AuthenticationResp;
import edu.fudan.dto.response.UserPrivateResp;
import edu.fudan.dto.response.UserPublicResp;
import edu.fudan.exception.ParseJsonStringException;
import edu.fudan.exception.UserNotFoundException;
import edu.fudan.model.UserService;
import edu.fudan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.io.IOException;

@Controller
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * User registration request.
     *
     * @return user private DTO with email field
     */

    @PostMapping
    ResponseEntity<UserPrivateResp> register(@RequestParam("avatar") MultipartFile avatar,
                                             @RequestParam("registerReq") String jsonString) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        RegisterReq registerReq = null;
        try {
            registerReq = mapper.readValue(jsonString, RegisterReq.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParseJsonStringException();
        }
        UserPrivateResp userPrivateResp = this.userService.createUser(registerReq.getPhone(),
                registerReq.getName(), registerReq.getPassword(), registerReq.getEmail(), avatar);
        return new ResponseEntity<>(userPrivateResp, HttpStatus.CREATED);
    }


//    @PostMapping("/verification_code")
//    ResponseEntity<String> sendCode(@RequestBody MailRelatedReq email) {
//        String code = userService.registerVerification(email.getEmail());
//        mailService.sendMessage(email.getEmail(), "Your verification code is " + code + ", it will expire after 72h :)");
//        return new ResponseEntity<String>("Please check your mail", HttpStatus.OK);
//    }

    /**
     * Get meta data of the user with id equals to {uid}.
     * Both authorized and unauthorized requests are acceptable.
     * Only authorized currentUser with the same id as {uid} could get
     * user private data. Others get public data.
     *
     * @param uid, id of the user to get
     * @return user public data
     */
    @GetMapping("/{uid}")
    ResponseEntity<UserPublicResp> getUser(@PathVariable long uid) {
        UserPublicResp userPublicResp = this.userService.getUserPublic(uid);
        return new ResponseEntity<>(userPublicResp, HttpStatus.OK);
    }


//    @PostMapping("/forget_password")
//    ResponseEntity sendPasswordLink(@RequestBody MailRelatedReq email) {
//        AuthenticationResp authenticationResp = userService.createToken(email.getEmail());
//        String message = "Click following link to reset your password: \n" +
//                "www.coursegraph.top/reset_password?token=" +
//                authenticationResp.getAuthentication();
//        mailService.sendMessage(email.getEmail(), message);
//        return new ResponseEntity(null, HttpStatus.OK);
//    }

    @GetMapping("/{uid}/avatar")
    public ResponseEntity<InputStreamResource> downloadAvatar(@PathVariable long uid) {
        User user = userRepository.findById(uid).orElseThrow(
                () -> new UserNotFoundException(uid)
        );
        // Set header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + user.getAvatar() + "\"");
        return new ResponseEntity<>(userService.downloadAvatarofUser(uid), headers, HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    @Authorization
    ResponseEntity resetPassword(@CurrentUser User user, @RequestParam("password") @Pattern(regexp = Application.PASSWORD_REGEX) String password) {
        userService.resetPassword(user, password);
        return new ResponseEntity(null, HttpStatus.OK);
    }


}
