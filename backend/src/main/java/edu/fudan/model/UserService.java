package edu.fudan.model;

import edu.fudan.Utils;
import edu.fudan.domain.TokenEntry;
import edu.fudan.domain.User;
import edu.fudan.dto.request.ResetPasswordReq;
import edu.fudan.dto.response.AuthenticationResp;
import edu.fudan.dto.response.UserPrivateResp;
import edu.fudan.dto.response.UserPublicResp;
import edu.fudan.exception.*;
import edu.fudan.repository.TokenRepository;
import edu.fudan.repository.UserRepository;
import edu.fudan.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    @Value("${avatar.dir.path}")
    private String avatarFilePath;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository,
                       VerificationCodeRepository verificationCodeRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.verificationCodeRepository = verificationCodeRepository;
    }

    /**
     * Given phone and password, create a token entry and return it as a authentication response
     *
     * @param phone,    phone of the account
     * @param password, password of the account
     * @return a string of authentication
     * @throws PhoneOrPasswordException, when phone not exists or password not match
     */
    public AuthenticationResp createToken(String phone, String password) {
        // Lower case
        phone = phone.toLowerCase();

        User user = this.userRepository.findByPhone(phone).orElseThrow(
                PhoneOrPasswordException::new // Email does not exist
        );

        if (!user.getPassword().equals(password)) {  // Wrong password
            throw new PhoneOrPasswordException();
        }

        // Generate a token
        TokenEntry tokenEntry = tokenRepository.createToken(user.getUserId());

        // Generate authentication from this token
        String authentication = tokenRepository.getAuthentication(tokenEntry);

        return new AuthenticationResp(authentication, new UserPublicResp(user));
    }

    public AuthenticationResp createToken(String phone) {
        phone = phone.toLowerCase();
        User user = this.userRepository.findByPhone(phone).orElseThrow(
                PhoneOrPasswordException::new // Email does not exist
        );

        // Generate a token
        TokenEntry tokenEntry = tokenRepository.createToken(user.getUserId());

        // Generate authentication from this token
        String authentication = tokenRepository.getAuthentication(tokenEntry);

        return new AuthenticationResp(authentication, new UserPublicResp(user));
    }

    /**
     * Given the user id, delete the token from repository
     *
     * @param userId, id of the current user
     */
    public void deleteToken(long userId) {
        tokenRepository.deleteToken(userId);
    }

    /**
     * Get user public data by id.
     *
     * @return the public data of the user
     * @throws UserNotFoundException, when user not found
     */
    public UserPublicResp getUserPublic(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );

        return new UserPublicResp(user);
    }

    /**
     * Get user private data by id
     *
     * @return the private data of the user
     * @throws UserNotFoundException, when user not found
     */
    public UserPrivateResp getUserPrivate(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId) // User not found
        );
        return new UserPrivateResp(user);
    }

    /**
     * Create a new user
     *
     * @return user private data
     * @throws PhoneConflictException, when phone already existed
     */
    public UserPrivateResp createUser(String phone, String name, String password, String email, MultipartFile file) {
        // Lower case
        phone = phone.toLowerCase();

        // Check if the phone exists
        if (this.userRepository.findByPhone(phone).isPresent()) {
            throw new PhoneConflictException(phone);
        }

        // Create a new id for user
        long newUserId = this.generateRandomId();

        // Create a new user based on the user type
        User newUser = new User(newUserId, name, password, phone, email);


        String avatar = "";
        // Save file (avatar image)
        avatar = Utils.saveFile(file, newUserId, avatarFilePath);

        // set avatar, if no avatar then avatar = ""
        newUser.setAvatar(avatar);

        // Add this newly created user to user repository
        User savedUser = userRepository.save(newUser);

        // Return a user private data
        return new UserPrivateResp(savedUser);
    }


    /**
     * Update the user meta data
     *
     * @param currentUser, id of the user
     * @param name         of the user
     * @param phone        of the user
     * @param password,    old password, required
     * @param newPassword, new password
     */
    public void updateUser(User currentUser,
                           @Nullable String name,
                           @Nullable String phone,
                           @NotNull String password,
                           @Nullable String newPassword) {
        // Check if the password matches
        if (!currentUser.getPassword().equals(password)) {
            throw new PhoneConflictException();
        }

        if (name != null) {
            // Change name
            currentUser.setName(name);
        }

        if (phone != null) {
            // Do NOT need to check phone pattern, because controller has done the job
            // Lower case
            phone = phone.toLowerCase();
            // Check conflict
            if (userRepository.findByPhone(phone).isPresent()) {
                throw new PhoneConflictException(phone);
            }
            // Change phone
            currentUser.setPhone(phone);
        }

        if (newPassword != null) {
            // Change password
            currentUser.setPassword(newPassword);

            // Remove authentication token to deleteToken user
            tokenRepository.deleteToken(currentUser.getUserId());
        }

        // Save the result to database
        userRepository.save(currentUser);
    }

    /**
     * Delete the user.
     *
     * @param currentUser, current login user
     */
    public void deleteUser(User currentUser) {
        // Delete token
        tokenRepository.deleteToken(currentUser.getUserId());

        // Delete user
        userRepository.delete(currentUser);
    }


    public InputStreamResource downloadAvatarofUser(long uid) {
        // Lecture must exist
        User user = userRepository.findById(uid).orElseThrow(
                () -> new UserNotFoundException()
        );

        if (user.getAvatar() == null || user.getAvatar().equals("")) {
            throw new UserAvatarNotExistException(uid);
        }

        //return a file stream to controller rather than all bytes of the file
        //to handle large files not only small files. when faced with file problems,
        //always steam, never keep fully in memory
        File avatarFile = new File(Paths.get(avatarFilePath + user.getAvatar()).toString());

        try {
            return new InputStreamResource(new FileInputStream(avatarFile));
        } catch (FileNotFoundException e) {
            throw new ImageIOException();
        }
    }


    /**
     * Generate a unique user id
     *
     * @return a user id
     */
    private long generateRandomId() {
        while (true) {
            long randomLong = RandomIdGenerator.getInstance().generateRandomLongId();
            // Check if the id exists as a user id
            if (!this.userRepository.existsById(randomLong)) {
                return randomLong;
            }
        }
    }

    public String registerVerification(String phone) {
        //first check whether the user existed
        if (userRepository.findByPhone(phone).isPresent())
            throw new PhoneConflictException();

        return verificationCodeRepository.createVerificationCode(phone);
    }

    public void resetPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    public String resetPassword(User user, ResetPasswordReq resetPasswordReq){
        if(!resetPasswordReq.getOldPassword().equals(user.getPassword()))
            return "Wrong Password";

        user.setPassword(resetPasswordReq.getNewPassword());
        userRepository.save(user);
        return "Success";
    }



}
