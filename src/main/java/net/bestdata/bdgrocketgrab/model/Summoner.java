package net.bestdata.bdgrocketgrab.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summoner")
@Data
public class Summoner {
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    private LeagueBySummoner[] leaguesBySummoners;
}