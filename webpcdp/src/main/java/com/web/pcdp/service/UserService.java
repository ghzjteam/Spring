package com.web.pcdp.service;

import com.web.pcdp.domain.User;
import com.web.pcdp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("user")
public class UserService {

    @Autowired

    private UserRepository userRepository;

    public User findUser(int user_id){
        User user = null;
        user = userRepository.finduser(user_id);
        if (user==null){
            return null;
        }
        else {
            return user;
        }

    }

}
