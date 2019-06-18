package com.web.pcdp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.pcdp.repository.UserRepository;

@Service
public class UserService {
	@Resource 
	private UserRepository usersRepository;
	
}
