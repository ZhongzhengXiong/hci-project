package edu.fudan.model;

import edu.fudan.domain.Activity;
import edu.fudan.domain.ActivityPhoto;
import edu.fudan.domain.User;
import edu.fudan.dto.response.ActivityPhotoResp;
import edu.fudan.repository.ActivityPhotoRepository;
import edu.fudan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActivityPhotoService {
    private final ActivityPhotoRepository activityPhotoRepository;
    private final UserRepository userRepository;

    @Value("${activity_photo.dir.path}")
    private String photoDir;


    @Autowired
    public ActivityPhotoService(ActivityPhotoRepository activityPhotoRepository,
                                UserRepository userRepository) {
        this.activityPhotoRepository = activityPhotoRepository;
        this.userRepository = userRepository;
    }


    public List<ActivityPhotoResp> getAllPhotosfUser(User currentUser) {
        List<ActivityPhoto> activityPhotos = activityPhotoRepository.
                findActivityPhotosByUserId(currentUser.getUserId());
        List<ActivityPhotoResp> activityPhotoResps = new ArrayList<>();
        for(ActivityPhoto activityPhoto: activityPhotos){
            activityPhotoResps.add(new ActivityPhotoResp(activityPhoto));
        }
        return activityPhotoResps;
    }



}
