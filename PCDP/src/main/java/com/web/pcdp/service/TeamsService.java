package com.web.pcdp.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.pcdp.entity.Teams;
import com.web.pcdp.repository.TeamsRepository;
@Service
public class TeamsService {
	@Autowired
	TeamsRepository teamsRepository;
	public List<Teams> findAll() {
		List<Teams> ul = new ArrayList<>();
		
		
		teamsRepository.findAll();
		Iterable<Teams> ui = teamsRepository.findAll();
		for(Teams item :ui) {
			System.out.println(item.getTeamId());
		}
		return ul;
	}
	@Transactional
	public void save() {
		Teams t = new Teams();
		t.setTeamId(4);
		t.setNote("fefew");
		t.setTeamName("Эѕеп");
		teamsRepository.save(t);
	}
}
