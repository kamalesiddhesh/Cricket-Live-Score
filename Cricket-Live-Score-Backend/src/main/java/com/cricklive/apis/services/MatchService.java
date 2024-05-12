package com.cricklive.apis.services;

import java.util.List;
import java.util.Map;

import com.cricklive.apis.entities.Match;

public interface MatchService {
	
// get All Matches	
	List<Match> getAllMatches();
	
// get Live matches
	List<Match> getLiveMatches();
	
// get Point Table 
	List<List<String>> getPointTable(); 

	

}
