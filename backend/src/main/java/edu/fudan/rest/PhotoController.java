package edu.fudan.rest;


import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.ActivityPhoto;
import edu.fudan.domain.User;
import edu.fudan.exception.ActivityPhotoNotFoundException;
import edu.fudan.model.ActivityPhotoService;
import edu.fudan.repository.ActivityPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/photo")
public class PhotoController {
    private final ActivityPhotoRepository activityPhotoRepository;
    private final ActivityPhotoService activityPhotoService;

    @Autowired
    public PhotoController(ActivityPhotoRepository activityPhotoRepository,
                           ActivityPhotoService activityPhotoService){
        this.activityPhotoRepository = activityPhotoRepository;
        this.activityPhotoService = activityPhotoService;
    }

    @GetMapping("/{pid}")
    @Authorization
    public ResponseEntity<InputStreamResource> downloadAvatar(@CurrentUser User user, @PathVariable long pid) {
        ActivityPhoto photo = activityPhotoRepository.findById(pid).orElseThrow(
                () -> new ActivityPhotoNotFoundException(pid)
        );
        // Set header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + photo.getPhotoName() + "\"");
        return new ResponseEntity<>(activityPhotoService.downloadPhoto(user, pid), headers, HttpStatus.OK);
    }

}
