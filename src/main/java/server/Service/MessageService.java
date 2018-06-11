package server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entity.CoreMessage;
import server.Repository.MessageRepository;
import server.model.MessageModel;

import java.util.List;

/**
 * Created by mars on 6/3/2018.
 */
@Service
public class MessageService implements IMessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public CoreMessage save(CoreMessage message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageModel> loadMessages(int channelId, int take, int skip) {
        Page<MessageModel> pages = messageRepository.findByChatId(channelId, new PageRequest(skip, take, Sort.Direction.ASC, "messageId"));
        return pages.getContent();
//        return null;
    }


}
