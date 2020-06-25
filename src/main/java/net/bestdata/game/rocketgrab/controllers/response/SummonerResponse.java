package net.bestdata.game.rocketgrab.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SummonerResponse {
    private String name;
    private int profileIconId;
    private long summonerLevel;
    private CurrentSeason currentSeason;
}
