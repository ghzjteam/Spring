package com.web.pcdp.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.pcdp.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
}
