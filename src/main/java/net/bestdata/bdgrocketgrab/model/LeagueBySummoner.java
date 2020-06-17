package net.bestdata.bdgrocketgrab.model;

import lombok.Data;

@Data
public class LeagueBySummoner {
    String leagueId;
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
