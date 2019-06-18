package com.web.pcdp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.entity.User;
import com.web.pcdp.repository.UserRepository;
@Service
public class UserQuery {
	@Autowired
	UserRepository usersRepository;
	
	public List<User> findAll() {
		List<User> ul = new ArrayList<>();
		usersRepository.findAll();
		Iterable<User> ui = usersRepository.findAll();
		for(User item :ui) {
			System.out.println(item.getUserId());
		}
		return ul;
	}
}
