package com.web.pcdp.repository;


import com.web.pcdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //根据用户ID查询用户信息
    @Query(value = "SELECT * FROM user WHERE user_id = ?", nativeQuery = true)
    User finduser(@Param("user_id") int id);

}