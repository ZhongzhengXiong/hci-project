package edu.fudan.model;

import edu.fudan.Utils;
import edu.fudan.domain.*;
import edu.fudan.dto.request.CreateNoticeReq;
import edu.fudan.dto.request.CreateReviewReq;
import edu.fudan.dto.request.CreateorUpdateActivityReq;
import edu.fudan.dto.response.*;
import edu.fudan.exception.ActivityNotFoundException;
import edu.fudan.exception.DuplicateUserException;
import edu.fudan.exception.InvalidInvitingcodeException;
import edu.fudan.exception.PermissionDeniedException;
import edu.fudan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.*;

@Service
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final NoticeRepository noticeRepository;

    private final MessageRepository messageRepository;

    private final ActivityPhotoRepository activityPhotoRepository;

    private final PermissionService permissionService;

    private final MessagesService messagesService;

    @Value("${activity_photo.dir.path}")
    private String activityPhotoDir;

    @Value("${intro_photo.dir.path}")
    private String introPhotoDir;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ReviewRepository reviewRepository,
                           UserRepository userRepository, NoticeRepository noticeRepository,
                           MessageRepository messageRepository, ActivityPhotoRepository activityPhotoRepository,
                           PermissionService permissionService, MessagesService messagesService) {
        this.activityRepository = activityRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
        this.messageRepository = messageRepository;
        this.activityPhotoRepository = activityPhotoRepository;
        this.permissionService = permissionService;
        this.messagesService = messagesService;
    }

    public List<ActivityMetaResp> getAllActivities() {
        List<ActivityMetaResp> activityMetaResps = new ArrayList<>();
        for (Activity activity : activityRepository.findAll()) {
            ActivityMetaResp activityMetaResp = new ActivityMetaResp(activity);
            activityMetaResps.add(activityMetaResp);
        }
        return activityMetaResps;
    }

    public List<ActivityMetaResp> getAllActivitiesOfUser(User user) {
        List<ActivityMetaResp> activityMetaResps = new ArrayList<>();
        for (Activity activity : user.getParticipatedActivies()) {
            ActivityMetaResp activityMetaResp = new ActivityMetaResp(activity);
            activityMetaResps.add(activityMetaResp);
        }
        return activityMetaResps;
    }


    public ActivityPrivateResp createActivity(User user, CreateorUpdateActivityReq createActivityReq, MultipartFile file) {

        long activityId = RandomIdGenerator.getInstance().generateRandomLongId(activityRepository);
        Activity activity = new Activity(activityId, user);

        activity.setName(createActivityReq.getName());
        activity.setDetail(createActivityReq.getDetail());
        activity.setBeginDate(createActivityReq.getBeginDate());
        activity.setEndDate(createActivityReq.getEndDate());
        activity.setPlace(createActivityReq.getPlace());
        activity.setActivityType(createActivityReq.getType());
        activity.setStatus(true);
        activity.setUserLimit(createActivityReq.getUserLimit());

        // generate inviting code
        String invitingCode = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        activity.setInvitingCode(invitingCode);


        String introPhotoName = "";
        introPhotoName = Utils.saveFile(file, activityId, introPhotoDir);
        activity.setIntroPhotoName(introPhotoName);

        Activity savedActivity = activityRepository.save(activity);

        // create new message
        String content = MessageFormat.format(messagesService.createActivityTemplate, invitingCode);
        messagesService.createMessage(user, content, MessageType.CREATOR, new Date(), activityId);


        ActivityPrivateResp activityPrivateResp = new ActivityPrivateResp(savedActivity);
        return activityPrivateResp;

    }


    public ActivityStatusResp updateActivityStatus(User user, long activityId, boolean status) {
        // First the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check permission
        if (!permissionService.checkWritePermOfActivity(user, activityId))
            throw new PermissionDeniedException();

        activity.setStatus(status);
        Activity newActivity = activityRepository.save(activity);

        //create new  message
        String content;
        if (!status)
            content = MessageFormat.format(messagesService.closeActivityTemplate, user.getName(), activity.getName());
        else
            content = MessageFormat.format(messagesService.reopenActivityTemplate, user.getName(), activity.getName());

        messagesService.createMessage(user, content, MessageType.PARTICIPATOR, new Date(), activityId);


        return new ActivityStatusResp(newActivity.getActivityId(), newActivity.getStatus());

    }

    public ActivityMetaResp getActivity(User user, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // for user as the participator of this activity
        if (permissionService.checkReadPermOfActivity(user, activityId)) {
            return new ActivityPrivateResp(activity);
        }
        return new ActivityPublicResp(activity);
    }


    public ActivityPrivateResp updateActivity(User user, long activityId, CreateorUpdateActivityReq updateActivityReq, MultipartFile file) {
        //First, the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        //The user must have writing permission of the user
        if (!permissionService.checkWritePermOfActivity(user, activityId))
            throw new PermissionDeniedException();

        // update activity
        activity.setName(updateActivityReq.getName());
        activity.setDetail(updateActivityReq.getDetail());
        activity.setBeginDate(updateActivityReq.getBeginDate());
        activity.setEndDate(updateActivityReq.getEndDate());
        activity.setPlace(updateActivityReq.getPlace());
        activity.setUserLimit(updateActivityReq.getUserLimit());
        activity.setActivityType(updateActivityReq.getType());


        activity.setIntroPhotoName(Utils.saveFile(file, activityId, introPhotoDir));
        // save update
        Activity newActivity = activityRepository.save(activity);

        // create new message
        String content = MessageFormat.format(messagesService.updateActivityTemplate, user.getName(), activity.getName());
        messagesService.createMessage(user, content, MessageType.PARTICIPATOR, new Date(), activityId);

        return new ActivityPrivateResp(newActivity);
    }


    public List<UserPublicResp> getAllUsersOfActivity(User user, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        List<UserPublicResp> userPublicResps = new ArrayList<>();
        for (User user1 : activity.getParticipators()) {
            userPublicResps.add(new UserPublicResp(user1));
        }
        return userPublicResps;
    }

    public ActivityPrivateResp addUserToActivity(User user, long activityId, String invitingCode) {
        // First, the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException()
        );

        //Inviting code must be same with the activity's one
        if (invitingCode == null || !invitingCode.equals(activity.getInvitingCode())) {
            throw new InvalidInvitingcodeException();
        }

        //Current user must have not participated in the activity
        if (activity.getParticipators().contains(user)) {
            throw new DuplicateUserException();
        }

        //Add user to activity
        activity.addParticipator(user);


        Activity newActivity = activityRepository.save(activity);

        //create new message
        String content = MessageFormat.format(messagesService.participateActivityTemplate, user.getName(), activity.getName());
        messagesService.createMessage(user, content, MessageType.PARTICIPATOR, new Date(), activityId);

        return new ActivityPrivateResp(newActivity);
    }

    public List<NoticeResp> getAllNoticesOfActivity(User user, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );

        //check permission
        if (!permissionService.checkReadPermOfActivity(user, activityId)) {
            throw new PermissionDeniedException();
        }

        List<NoticeResp> noticeResps = new ArrayList<>();
        for (Notice notice : activity.getNotices()) {
            noticeResps.add(new NoticeResp(notice));
        }
        return noticeResps;
    }

    public NoticeResp createNotice(User user, long activityId, CreateNoticeReq createNoticeReq) {
        // check whether the activity exists
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check permission
        if (!permissionService.checkReadPermOfActivity(user, activityId))
            throw new PermissionDeniedException();

        long noticeId = RandomIdGenerator.getInstance().generateRandomLongId(noticeRepository);

        Notice notice = new Notice(noticeId, createNoticeReq.getContent(), createNoticeReq.getTitle());

        Notice newNotice = noticeRepository.save(notice);
        activity.addNotice(newNotice);
        activityRepository.save(activity);


        //create new message
        String content = MessageFormat.format(messagesService.releaseNoticeTemplate, activity.getName());
        messagesService.createMessage(user, content, MessageType.PARTICIPATOR, new Date(), activityId);


        return new NoticeResp(newNotice);
    }


    public List<ReviewResp> getAllReviewsOfActivity(User user, long activityId) {
        // the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check read perm
        if (!permissionService.checkReadPermOfActivity(user, activityId)) {
            throw new PermissionDeniedException();
        }
        List<ReviewResp> reviewResps = new ArrayList<>();

        for (Review review : activity.getReviews()) {
            reviewResps.add(new ReviewResp(review.getReviewId(), review.getUserId(),
                    review.getContent(), review.getDate()));
        }
        return reviewResps;
    }


    public ReviewResp createReview(User user, long activityId, CreateReviewReq createReviewReq) {
        // the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check read perm
        if (!permissionService.checkReadPermOfActivity(user, activityId)) {
            throw new PermissionDeniedException();
        }

        long reviewId = RandomIdGenerator.getInstance().generateRandomLongId(reviewRepository);

        Review review = new Review(reviewId, createReviewReq.getContent(), user.getUserId());

        Review newReview = reviewRepository.save(review);
        activity.addReviews(newReview);
        activityRepository.save(activity);

        return new ReviewResp(newReview);

    }


    public StatisticsInfoResp getStatisticsOfUser(User currentUser) {
        StatisticsInfoResp statisticsInfoResp = new StatisticsInfoResp();

        statisticsInfoResp.setCreateNum(currentUser.getCreatedActivities().size());

        List<Activity> activities = currentUser.getParticipatedActivies();
        statisticsInfoResp.setParticipateNum(activities.size());

        HashMap<ActivityType, Integer> activityTypeStats = new HashMap<>();

        for (Activity activity : activities) {
            ActivityType activityType = activity.getActivityType();
            Integer num = activityTypeStats.get(activityType);
            if (num == null)
                activityTypeStats.put(activityType, 0);
            else
                activityTypeStats.put(activityType, num + 1);
        }

        statisticsInfoResp.setActivityTypeStats(activityTypeStats);

        statisticsInfoResp.setReviewNum(reviewRepository.
                findReviewsByUserId(currentUser.getUserId()).size());

        statisticsInfoResp.setUploadPhotoNum(activityPhotoRepository.
                findActivityPhotosByUserId(currentUser.getUserId()).size());

        Set<String> placeSet = new HashSet<>();

        for (Activity activity : activities) {
            placeSet.add(activity.getPlace());
        }
        statisticsInfoResp.setPlaceSet(placeSet);
        return statisticsInfoResp;
    }

}
