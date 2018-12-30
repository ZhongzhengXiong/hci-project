package edu.fudan.model;

import edu.fudan.domain.Message;
import edu.fudan.domain.User;
import edu.fudan.dto.response.MessageResp;
import edu.fudan.exception.MessageNotFoundException;
import edu.fudan.exception.PermissionDeniedException;
import edu.fudan.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessagesService {
    private MessageRepository messageRepository;
    private final PermissionService permissionService;

    @Autowired
    public MessagesService(MessageRepository messageRepository,
                           PermissionService permissionService) {
        this.messageRepository = messageRepository;
        this.permissionService = permissionService;
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

}
