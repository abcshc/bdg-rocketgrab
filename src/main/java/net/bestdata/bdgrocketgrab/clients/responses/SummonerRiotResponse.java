package net.bestdata.bdgrocketgrab.clients.responses;

import lombok.Data;
import net.bestdata.bdgrocketgrab.model.Summoner;

// SummonerDTO
@Data
public class SummonerRiotResponse {
    String accountId;
    int profileIconId;
    long revisionDate;
    String name;
    String id;
    String puuid;
    long summonerLevel;

    public Summoner convertToSummoner() {
        return overwriteSummoner(new Summoner());
    }

    public Summoner overwriteSummoner(Summoner summoner) {
        summoner.setAccountId(accountId);
        summoner.setProfileIconId(profileIconId);
        summoner.setRevisionDate(revisionDate);
        summoner.setName(name);
        summoner.setId(id);
        summoner.setPuuid(puuid);
        summoner.setSummonerLevel(summonerLevel);
        return summoner;
    }
}
