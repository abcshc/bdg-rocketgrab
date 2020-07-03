package net.bestdata.game.rocketgrab.clients.responses;

import lombok.Getter;
import lombok.Setter;

// SummonerDTO
@Getter
@Setter
public class SummonerRiotResponse {
    String accountId;
    int profileIconId;
    long revisionDate;
    String name;
    String id;
    String puuid;
    long summonerLevel;
}
