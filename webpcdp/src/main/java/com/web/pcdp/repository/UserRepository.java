package com.web.pcdp.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository {
}
=======

import com.web.pcdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //根据用户ID查询用户信息
    @Query(value = "SELECT * FROM user WHERE user_id = ?", nativeQuery = true)
    User finduser(@Param("user_id") int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USER(user_id,user_name,password,email,photo,phone,gender,reg_date)VALUES(?,?,?,?,?,?,?,?)", nativeQuery = true)
    void insertUser(@Param("user_id") int user_id,
                    @Param("user_name") String user_name,
                    @Param("password") String password,
                    @Param("email") String email,
                    @Param("photo") String photo,
                    @Param("phone") String phone,
                    @Param("gender") String gender,
                    @Param("reg_date") String reg_date);


    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET user_name =?, password=?,email=?,photo=?,phone=?,gender=?,reg_date = ? WHERE user_id =?;", nativeQuery = true)
    void alterUser(@Param("user_id") int user_id,
                    @Param("user_name") String user_name,
                    @Param("password") String password,
                    @Param("email") String email,
                    @Param("photo") String photo,
                    @Param("phone") String phone,
                    @Param("gender") String gender,
                    @Param("reg_date") String reg_date);



}
>>>>>>> c03938eddc2acb005e8a348f4ae7b480aae981f6
