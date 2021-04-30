package com.mehul.ipldashboard.listener;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mehul.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	private EntityManager em;

	@Override	
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");
			
			/**
			 * This Map hold team wise information of how many matches they played and from them how many wines  
			 */
			Map<String, Team> teamData = new HashMap<>();
			
			/**
			 * This JPQL fetch data of team who played first inning(Bat)
			 */
			em.createQuery("select distinct m.team1 , count(*) from Match m group by m.team1", Object[].class)
					.getResultList()
					.stream()
					.map(resultObj -> new Team((String) resultObj[0], (Long) resultObj[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));
			
			/**
			 * This JPQL fetch data of team who played second inning(Ball)
			 */
			em.createQuery("select distinct m.team2 , count(*) from Match m group by m.team2", Object[].class)
			.getResultList()
			.stream()
			.forEach(resultObj->{
				Team team = teamData.get((String)resultObj[0]);
				team.setTotalMatch(team.getTotalMatch() + (Long)resultObj[1]);
			});
	
			/**
			 * This JPQL fetch data of how many times particular team is win the match
			 */
			em.createQuery("select distinct m.matchWinner , count(*) from Match m group by m.matchWinner", Object[].class)
			.getResultList()
			.stream()
			.forEach(resultObj->{
				Team team = teamData.get((String)resultObj[0]);
				if(team !=null) {
					team.setTotalWins((Long)resultObj[1]);
				}
			});
			
			/**
			 * This will persist calculated match data in DB
			 */
			teamData.values().forEach(team->{
				System.out.println("team is :"+team.toString());
				em.persist(team);
			});
		}
	}
}
