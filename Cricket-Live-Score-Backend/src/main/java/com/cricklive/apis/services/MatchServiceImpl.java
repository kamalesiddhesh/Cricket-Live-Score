package com.cricklive.apis.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricklive.apis.entities.Match;
import com.cricklive.apis.repositories.MatchRepo;

@Service
public class MatchServiceImpl implements MatchService{
	
	private MatchRepo matchRepo;
	
	@Autowired
	public MatchServiceImpl(MatchRepo matchRepo) {
		this.matchRepo = matchRepo;
	}
	
// Return match history(all matches) from our database
	@Override
	public List<Match> getAllMatches() {		
		return this.matchRepo.findAll();
	}
	
// Return All Live Matches......................
	@Override
	public List<Match> getLiveMatches() {
		
		List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();
                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeamHeading(teamsHeading);
                match1.setMatchNumberVenue(matchNumberVenue);
                match1.setBattingTeam(battingTeam);
                match1.setBattingTeamScore(score);
                match1.setBowlTeam(bowlTeam);
                match1.setBowlTeamScore(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();


                matches.add(match1);

//              update the match in database
            	updateMatch(match1);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;

	}
	
	private void updateMatch(Match match1) {
// 		update match in Database
		
		Match match = this.matchRepo.findByTeamHeading(match1.getTeamHeading()).orElse(null);
		
		if(match == null) {
			matchRepo.save(match1);
		}
		else {
			match1.setMatchId(match.getMatchId());
			
			matchRepo.save(match1);
		}
		
	}
	
	@Override
	public List<List<String>> getPointTable() {
		
		return null;
	}

}
