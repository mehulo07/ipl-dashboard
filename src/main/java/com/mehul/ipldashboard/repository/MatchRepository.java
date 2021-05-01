package com.mehul.ipldashboard.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mehul.ipldashboard.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

	default List<Match> findLatestMatchesByTeamName(String teamName, int pageNo, int count) {
		return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(pageNo, count));
	}
}
