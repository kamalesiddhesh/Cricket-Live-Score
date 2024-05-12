package com.cricklive.apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cricklive.apis.entities.Match;
import com.cricklive.apis.services.MatchService;

@RestController
@RequestMapping("/match")
public class MatchController {
	
	private MatchService matchService;
	
	@Autowired
	public MatchController(MatchService matchService) {
		this.matchService = matchService;		
	}
	
	@GetMapping("/live")
	public ResponseEntity<List<Match>> getLiveMatches(){
		return new ResponseEntity<>(this.matchService.getLiveMatches(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Match>> getAllMatches(){		
		return new ResponseEntity<>(this.matchService.getAllMatches(),HttpStatus.OK);
	}
	
	@GetMapping("/point-table")
	public ResponseEntity<?> getPointTable(){
		return null;
		
	}
}
