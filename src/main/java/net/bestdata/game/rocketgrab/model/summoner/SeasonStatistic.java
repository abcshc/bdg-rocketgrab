package net.bestdata.game.rocketgrab.model.summoner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SeasonStatistic {
    private String dataVersion;
    private String tier;
    private int leaguePoints;
    private int wins;
    private int topFour;
    private int plays;
    private String averagePlacement;
}