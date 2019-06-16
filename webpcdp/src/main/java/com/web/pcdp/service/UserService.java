package com.web.pcdp.service;

import com.web.pcdp.domain.User;
import com.web.pcdp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Title: UserService.java 

* Description:   UserService

* @author Guo_Jinhang  

* @date 2019年6月16日  

* @version 1.0  
 */
@Service("user")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //根据user_id获取用户信息
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

    //用户注册
    public void insertUser(User user){
        User newuser = user;
        userRepository.insertUser(
                newuser.getUserId(),
                newuser.getUserName(),
                newuser.getPassword(),
                newuser.getEmail(),
                newuser.getPhoto(),
                newuser.getPhone(),
                newuser.getGender(),
                newuser.getRegDate());
    }

    //修改用户信息
    public void alterUser(User user){
        User newuser = user;
        userRepository.alterUser(
                newuser.getUserName(),
                newuser.getPassword(),
                newuser.getEmail(),
                newuser.getPhoto(),
                newuser.getPhone(),
                newuser.getGender(),
                newuser.getUserId());
        
    } 
    //修改密码
    public void updataPassword(User user){
        User newuser = user;
        userRepository.updataPassword(
                newuser.getPassword(),
                newuser.getUserId());
    } 
    //获得最大的ID
    public int getNextUserID(){
        return userRepository.findMaxUserID();
    }  
}
