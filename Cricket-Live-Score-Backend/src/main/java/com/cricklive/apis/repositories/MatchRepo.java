package com.cricklive.apis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cricklive.apis.entities.Match;

public interface MatchRepo extends JpaRepository<Match,Integer>{

// To fetch match 
// Provide teamHeading
	
	Optional<Match> findByTeamHeading(String teamHeading);

}
