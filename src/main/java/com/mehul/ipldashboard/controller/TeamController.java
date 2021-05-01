package com.mehul.ipldashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mehul.ipldashboard.model.Match;
import com.mehul.ipldashboard.model.Team;
import com.mehul.ipldashboard.repository.MatchRepository;
import com.mehul.ipldashboard.repository.TeamRepository;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		List<Match> matches = matchRepository.findLatestMatchesByTeamName(teamName, 0, 4);
		team.setMatches(matches);
		return team;
	}

}
