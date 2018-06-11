package server.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.Entity.CoreConversation;
import server.Entity.CoreMessage;

/**
 * Created by mars on 2/10/2018.
 */
public interface ConversationRepository extends JpaRepository<CoreConversation,Integer> {

    @Query("select ms from CoreConversation ms where ms.user.userId = :userId and ms.chatRoom.chatId= :chatId")
    Page<CoreConversation> isJoin(@Param("userId") Integer userId,@Param("chatId")Integer chatId, Pageable pageRequest);
}
