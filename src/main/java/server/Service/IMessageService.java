package server.Service;

import org.springframework.stereotype.Service;
import server.Entity.CoreMessage;
import server.model.MessageModel;

import java.util.List;

/**
 * Created by mars on 6/3/2018.
 */
@Service
public interface IMessageService  {

    CoreMessage save(CoreMessage message);

    List<MessageModel> loadMessages(int channelId, int take, int skip);
}
