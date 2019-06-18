package com.web.pcdp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.entity.Team;
import com.web.pcdp.repository.TeamRepository;
@Service
public class TeamService {
	@Autowired
	TeamRepository teamsRepository;
	public List<Team> findAll() {
		List<Team> ul = new ArrayList<>();
		
		
		teamsRepository.findAll();
		Iterable<Team> ui = teamsRepository.findAll();
		for(Team item :ui) {
			System.out.println(item.getTeamId());
		}
		return ul;
	}
	@Transactional
	public void save() {
		Team t = new Team();
		t.setTeamId(4);
		t.setNote("fefew");
		t.setTeamName("����");
		teamsRepository.save(t);
	}
}
