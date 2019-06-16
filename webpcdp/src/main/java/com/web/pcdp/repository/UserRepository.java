package com.web.pcdp.repository;


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
    
    @Query(value = "SELECT MAX(user_id) FROM user", nativeQuery = true)
    int findMaxUserID();
    
    //用户注册
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

    //修改用户信息（包括修改密码）
    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET user_name =?, password=?,email=?,photo=?,phone=?,gender=? WHERE user_id =?;", nativeQuery = true)
    void alterUser(
                   @Param("user_name") String user_name,
                   @Param("password") String password,
                   @Param("email") String email,
                   @Param("photo") String photo,
                   @Param("phone") String phone,
                   @Param("gender") String gender,
                   @Param("user_id") int user_id);

    //修改用户修改密码）
    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET password=? WHERE user_id =?;", nativeQuery = true)
    void updataPassword(
                   @Param("password") String password,
                   @Param("user_id") int user_id);
}