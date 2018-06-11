package server.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.Entity.CoreMessage;
import server.model.MessageModel;

import java.util.List;

/**
 * Created by mars on 2/9/2018.
 */
public interface MessageRepository extends JpaRepository<CoreMessage,Integer> {

    @Query("select new server.model.MessageModel(ms.messageId,ms.messageContext,ms.messageDate,ms.messagePath,ms.messageType) from CoreMessage ms  where ms.chatRoom.chatId = :chatId")
    Page<MessageModel> findByChatId(@Param("chatId") Integer chatId, Pageable pageRequest);

    @Query("select  ms from CoreMessage ms where ms.messageId = :messageId")
    List<CoreMessage> findByMessageId(@Param("messageId") Integer messageId);




}
