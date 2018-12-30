package edu.fudan.model;

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

    @Value("${activity_photo.dir.path}")
    private String photoDir;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ReviewRepository reviewRepository,
                           UserRepository userRepository, NoticeRepository noticeRepository,
                           MessageRepository messageRepository, ActivityPhotoRepository activityPhotoRepository,
                           PermissionService permissionService) {
        this.activityRepository = activityRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
        this.messageRepository = messageRepository;
        this.activityPhotoRepository = activityPhotoRepository;
        this.permissionService = permissionService;
    }

    public List<ActivityMetaResp> getAllActivities() {
        List<ActivityMetaResp> activityMetaResps = new ArrayList<>();
        for (Activity activity : activityRepository.findAll()) {
            //todo: generate photoLink
            String photoLink = "";
            ActivityMetaResp activityMetaResp = new ActivityMetaResp(activity, photoLink);
            activityMetaResps.add(activityMetaResp);
        }
        return activityMetaResps;
    }

    public List<ActivityMetaResp> getAllActivitiesOfUser(User user){
        List<ActivityMetaResp> activityMetaResps = new ArrayList<>();
        for(Activity activity : user.getParticipatedActivies()){
            String photoLink = "";
            ActivityMetaResp activityMetaResp = new ActivityMetaResp(activity, photoLink);
            activityMetaResps.add(activityMetaResp);
        }
        return activityMetaResps;
    }



    public ActivityPrivateResp createActivity(User user, CreateorUpdateActivityReq createActivityReq) {

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

        Activity newActivity = activityRepository.save(activity);

        //todo create message

        //todo: generate photo link
        String photoLink = "";
        ActivityPrivateResp activityPrivateResp = new ActivityPrivateResp(newActivity, photoLink);
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

        // todo create message

        return new ActivityStatusResp(newActivity.getActivityId(), newActivity.getStatus());
    }

    public ActivityMetaResp getActivity(User user, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // for user as the participator of this activity
        if (permissionService.checkReadPermOfActivity(user, activityId)) {
            //todo
            return new ActivityPrivateResp(activity, generatePhotoLink());
        }
        return new ActivityPublicResp(activity, generatePhotoLink());
    }


    public ActivityPrivateResp updateActivity(User user, long activityId, CreateorUpdateActivityReq updateActivityReq) {
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

        // save update
        Activity newActivity = activityRepository.save(activity);
        return new ActivityPrivateResp(newActivity, generatePhotoLink());

        // todo create message
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

        //todo create message

        Activity newActivity = activityRepository.save(activity);
        //todo photoLink
        return new ActivityPrivateResp(newActivity, generatePhotoLink());
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

    public NoticeResp createNotice(User user, long activityId, CreateNoticeReq createNoticeReq){
        // check whether the activity exists
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                ()  -> new ActivityNotFoundException(activityId)
        );
        // check permission
        if(!permissionService.checkReadPermOfActivity(user, activityId))
            throw new PermissionDeniedException();

        long noticeId = RandomIdGenerator.getInstance().generateRandomLongId(noticeRepository);

        Notice notice = new Notice(noticeId, createNoticeReq.getContent(), createNoticeReq.getTitle());

        Notice newNotice = noticeRepository.save(notice);
        activity.addNotice(newNotice);
        activityRepository.save(activity);



        // todo create message
        return new NoticeResp(newNotice);
    }


    public List<ReviewResp> getAllReviewsOfActivity(User user, long activityId){
        // the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check read perm
        if(!permissionService.checkReadPermOfActivity(user, activityId)){
            throw new PermissionDeniedException();
        }
        List<ReviewResp> reviewResps = new ArrayList<>();

        for(Review review : activity.getReviews()){
            reviewResps.add(new ReviewResp(review.getReviewId(), review.getUserId(),
                    review.getContent(), review.getDate()));
        }
        return  reviewResps;
    }


    public ReviewResp createReview(User user, long activityId, CreateReviewReq createReviewReq){
        // the activity must exist
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        // check read perm
        if(!permissionService.checkReadPermOfActivity(user, activityId)){
            throw new PermissionDeniedException();
        }

        long reviewId = RandomIdGenerator.getInstance().generateRandomLongId(reviewRepository);

        Review review = new Review(reviewId, createReviewReq.getContent(), user.getUserId());

        Review newReview = reviewRepository.save(review);
        activity.addReviews(newReview);
        activityRepository.save(activity);
        // todo generate message

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
    // todo
    private String generatePhotoLink() {
        return "";
    }
}
