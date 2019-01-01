package edu.fudan.model;

import edu.fudan.domain.Activity;
import edu.fudan.domain.ActivityPhoto;
import edu.fudan.domain.User;
import edu.fudan.dto.response.ActivityPhotoResp;
import edu.fudan.exception.*;
import edu.fudan.repository.ActivityPhotoRepository;
import edu.fudan.repository.ActivityRepository;
import edu.fudan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ActivityPhotoService {
    private final ActivityPhotoRepository activityPhotoRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @Value("${activity_photo.dir.path}")
    private String photoDir;


    @Autowired
    public ActivityPhotoService(ActivityPhotoRepository activityPhotoRepository,
                                UserRepository userRepository,
                                ActivityRepository activityRepository) {
        this.activityPhotoRepository = activityPhotoRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }


    public List<ActivityPhotoResp> getAllPhotosfUser(User currentUser) {
        long userId = currentUser.getUserId();
        currentUser = userRepository.findById(userId, 2).orElseThrow(
                ()->new UserNotFoundException(userId)
        );
        List<ActivityPhoto> activityPhotos = currentUser.getActivityPhotos();
        List<ActivityPhotoResp> activityPhotoResps = new ArrayList<>();
        for(ActivityPhoto activityPhoto: activityPhotos){
//            Activity activity = activityRepository.findById(activityPhoto.getActivityId()).orElseThrow(
//                    () -> new ActivityNotFoundException()
//            );
//            activityPhoto.setActivity(activity);
            activityPhotoResps.add(new ActivityPhotoResp(activityPhoto));
        }
        activityPhotoResps.sort(new Comparator<ActivityPhotoResp>() {
            @Override
            public int compare(ActivityPhotoResp o1, ActivityPhotoResp o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return activityPhotoResps;
    }

    public InputStreamResource downloadPhoto(User user, long pid){

        ActivityPhoto photo = activityPhotoRepository.findById(pid).orElseThrow(
                () -> new ActivityNotFoundException()
        );
        if(!photo.getActivity().getParticipators().contains(user))
            throw new PermissionDeniedException();

        //return a file stream to controller rather than all bytes of the file
        //to handle large files not only small files. when faced with file problems,
        //always steam, never keep fully in memory
        File photoFile = new File(Paths.get(photoDir + photo.getPhotoName()).toString());

        try {
            return new InputStreamResource(new FileInputStream(photoFile));
        } catch (FileNotFoundException e) {
            throw new ImageIOException();
        }
    }



}
