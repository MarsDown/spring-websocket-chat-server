package server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.Entity.CoreConversation;
import server.Repository.ConversationRepository;

import java.util.List;

/**
 * Created by mars on 6/5/2018.
 */
@Service
public class ConversationService implements IConversationSerivce {

    @Autowired
    ConversationRepository conversationRepository;

    @Override
    public boolean isJoin(String channelId, String userName) {
        Page<CoreConversation> pages = conversationRepository.isJoin(Integer.parseInt(userName),Integer.parseInt(channelId), new PageRequest(0, 1000, Sort.Direction.DESC, "id"));
        List<CoreConversation> list = pages.getContent();

        return list.size() == 0 ? false : true;
    }
}
