package net.bestdata.game.rocketgrab.clients.responses;

import lombok.Getter;
import lombok.Setter;

// LeagueEntryDTO
@Getter
@Setter
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
}
