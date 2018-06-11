package server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.Entity.CoreChatRoom;

/**
 * Created by mars on 2/12/2018.
 */
public interface ChatRoomRepository extends JpaRepository<CoreChatRoom,Integer> {


}
