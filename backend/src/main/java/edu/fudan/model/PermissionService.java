package edu.fudan.model;

import edu.fudan.domain.Activity;
import edu.fudan.domain.Message;
import edu.fudan.domain.User;
import edu.fudan.exception.ActivityNotFoundException;
import edu.fudan.exception.MessageNotFoundException;
import edu.fudan.repository.ActivityRepository;
import edu.fudan.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionService {


    private final ActivityRepository activityRepository;

    private final MessageRepository messageRepository;



    @Autowired
    public PermissionService(ActivityRepository activityRepository, MessageRepository messageRepository) {
        this.activityRepository = activityRepository;
        this.messageRepository = messageRepository;
    }


    public boolean checkWritePermOfActivity(User user, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        return activity.getCreator().equals(user);
    }

    /**
     * Check read permission of the course
     * Here, read permission include review and uploading picture
     * @return true is have permission, false if not
     */
    public boolean checkReadPermOfActivity(User user, long activityId){
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        if(checkWritePermOfActivity(user, activityId))
            return true;
        return activity.getParticipators().contains(user);
    }

    public boolean checkPermOfMessage(User user, long messageId){
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new MessageNotFoundException(messageId)
        );
        return message.getUser().equals(user);
    }

}
