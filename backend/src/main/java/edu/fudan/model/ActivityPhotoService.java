package edu.fudan.model;

import edu.fudan.domain.Activity;
import edu.fudan.domain.ActivityPhoto;
import edu.fudan.domain.User;
import edu.fudan.dto.response.ActivityPhotoResp;
import edu.fudan.exception.*;
import edu.fudan.repository.ActivityPhotoRepository;
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
        List<ActivityPhoto> activityPhotos = currentUser.getActivityPhotos();
        List<ActivityPhotoResp> activityPhotoResps = new ArrayList<>();
        for(ActivityPhoto activityPhoto: activityPhotos){
            activityPhotoResps.add(new ActivityPhotoResp(activityPhoto));
        }
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
