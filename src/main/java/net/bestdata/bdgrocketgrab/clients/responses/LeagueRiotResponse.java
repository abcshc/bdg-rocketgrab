package net.bestdata.bdgrocketgrab.clients.responses;

import lombok.Data;
import net.bestdata.bdgrocketgrab.model.LeagueBySummoner;

// LeagueEntryDTO
@Data
public class LeagueRiotResponse {
    String leagueId;
    String summonerId;
    String summonerName;
    String queueType;
    String tier;
    String rank;
    int leaguePoints;
    int wins;
    int losses;
    boolean hotStreak;
    boolean veteran;
    boolean freshBlood;
    boolean inactive;

    public LeagueBySummoner convertToLeagueBySummoner() {
        LeagueBySummoner leagueBySummoner = new LeagueBySummoner();
        leagueBySummoner.setLeagueId(leagueId);
        leagueBySummoner.setQueueType(queueType);
        leagueBySummoner.setTier(tier);
        leagueBySummoner.setRank(rank);
        leagueBySummoner.setLeaguePoints(leaguePoints);
        leagueBySummoner.setWins(wins);
        leagueBySummoner.setLosses(losses);
        leagueBySummoner.setHotStreak(hotStreak);
        leagueBySummoner.setVeteran(veteran);
        leagueBySummoner.setFreshBlood(freshBlood);
        leagueBySummoner.setInactive(inactive);
        return leagueBySummoner;
    }
}
