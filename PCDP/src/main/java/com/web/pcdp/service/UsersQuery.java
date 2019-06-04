package com.web.pcdp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.entity.Users;
import com.web.pcdp.repository.UsersRepository;
@Service
public class UsersQuery {
	@Autowired
	UsersRepository usersRepository;
	
	public List<Users> findAll() {
		List<Users> ul = new ArrayList<>();
		usersRepository.findAll();
		Iterable<Users> ui = usersRepository.findAll();
		for(Users item :ui) {
			System.out.println(item.getUserId());
		}
		return ul;
	}
}
