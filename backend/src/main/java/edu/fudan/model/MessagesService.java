package edu.fudan.model;

import edu.fudan.domain.Activity;
import edu.fudan.domain.Message;
import edu.fudan.domain.MessageType;
import edu.fudan.domain.User;
import edu.fudan.dto.response.MessageResp;
import edu.fudan.exception.ActivityNotFoundException;
import edu.fudan.exception.MessageNotFoundException;
import edu.fudan.exception.PermissionDeniedException;
import edu.fudan.repository.ActivityRepository;
import edu.fudan.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessagesService {
    private MessageRepository messageRepository;
    private final PermissionService permissionService;
    private final ActivityRepository activityRepository;

    public final String createActivityTemplate = "you has created an activity (inviting code: {0}) successfully!";
    public final String updateActivityTemplate = "{0} recently updated the activity (name: {1})!";
    public final String closeActivityTemplate = "{0} has closed the activity (name: {1}).";
    public final String reopenActivityTemplate = "{0} reopended the activity (name: {1}) recently!";
    public final String releaseNoticeTemplate = "A new notice int activity (name: {0}) released just now";
    public final String participateActivityTemplate = "{0} joined in the activity (name: {1}) just now. Welcome!";
    public final String exitActivityTemplate = "{0} has exited the activity (name: {1}).";
    public final String uploadPhotoTemplate = "{0} uploaded a new photo in activity (name: {1}) just now!.";

    @Autowired
    public MessagesService(MessageRepository messageRepository,
                           PermissionService permissionService,
                           ActivityRepository activityRepository) {
        this.messageRepository = messageRepository;
        this.permissionService = permissionService;
        this.activityRepository = activityRepository;
    }

    public List<MessageResp> getAllMessagesOfUser(User currentUser){
        List<MessageResp> messageResps = new ArrayList<>();
        List<Message> messages = currentUser.getMessageList();
        for(Message message : messages){
            MessageResp messageResp = new MessageResp(message);
            messageResps.add(messageResp);
        }
        return messageResps;
    }


    public void deleteMessage(User currentUser, long messageId){
        if(!permissionService.checkPermOfMessage(currentUser, messageId)){
            throw new PermissionDeniedException();
        }
        messageRepository.deleteById(messageId);
    }

    public void updateMessageStatus(User currentUser, long messageId, boolean status){
        if(!permissionService.checkPermOfMessage(currentUser, messageId))
            throw new PermissionDeniedException();
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new MessageNotFoundException()
        );
        message.setStatus(status);
        messageRepository.save(message);
    }

    public void createMessage(User user, String content, MessageType messageType, Date date, long activityId){
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new ActivityNotFoundException(activityId)
        );
        long messageId = RandomIdGenerator.getInstance().generateRandomLongId(messageRepository);
        Message message = new Message(messageId, date, content, activity);

        List<User> participators = activity.getParticipators();
        if(!participators.contains(user))
            return;

        // add user according MessageType
        if(messageType.equals(MessageType.PARTICIPATOR)){
            List<User> users = activity.getParticipators();
            for(User user1: users)
                message.addUser(user1);
        }else if(messageType.equals(MessageType.CREATOR)){
            User creator = activity.getCreator();
            message.addUser(creator);
            if(!creator.equals(user))
                message.addUser(user);
        }
        messageRepository.save(message);
    }


}
