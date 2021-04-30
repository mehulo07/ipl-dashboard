package com.mehul.ipldashboard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String teamName;
	private Long totalMatch;
	private Long totalWins;

	public Team() {
		super();
	}

	public Team(String teamName, Long totalMatch) {
		super();
		this.teamName = teamName;
		this.totalMatch = totalMatch;
	}

	public Team(Long id, String teamName, Long totalMatch, Long totalWins) {
		super();
		Id = id;
		this.teamName = teamName;
		this.totalMatch = totalMatch;
		this.totalWins = totalWins;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getTotalMatch() {
		return totalMatch;
	}

	public void setTotalMatch(Long totalMatch) {
		this.totalMatch = totalMatch;
	}

	public Long getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}

	@Override
	public String toString() {
		return "Team [Id=" + Id + ", teamName=" + teamName + ", totalMatch=" + totalMatch + ", totalWins=" + totalWins
				+ "]";
	}

}
