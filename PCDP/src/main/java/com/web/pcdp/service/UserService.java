package com.web.pcdp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.pcdp.repository.UsersRepository;

@Service
public class UserService {
	@Resource 
	private UsersRepository usersRepository;
	
}
