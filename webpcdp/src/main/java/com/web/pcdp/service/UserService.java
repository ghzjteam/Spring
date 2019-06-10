package com.web.pcdp.service;

<<<<<<< HEAD
public class UserService {
=======
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

    public void insertUser(User user){

        User newuser = user;
        userRepository.insertUser(newuser.getUserId(),newuser.getUserName(),newuser.getPassword(),
                newuser.getEmail(),newuser.getPhoto(),newuser.getPhone(),newuser.getGender(), newuser.getRegDate());
    }

    public void alterUser(User user){
        User newuser = user;
        userRepository.alterUser(newuser.getUserId(),newuser.getUserName(),newuser.getPassword(),
                newuser.getEmail(),newuser.getPhoto(),newuser.getPhone(),newuser.getGender(), newuser.getRegDate());
    }

>>>>>>> c03938eddc2acb005e8a348f4ae7b480aae981f6
}
