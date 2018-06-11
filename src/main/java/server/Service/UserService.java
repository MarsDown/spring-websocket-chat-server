package server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.Entity.CoreUser;
import server.Repository.UserRepo;

import java.util.List;

/**
 * Created by mars on 6/3/2018.
 */
@Service
public class UserService implements IUserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean isUser(String userName, String token) {
        List<CoreUser> coreUsers = userRepo.findAllWithout(1);

        return coreUsers.size()==0?false:true;
    }
}
