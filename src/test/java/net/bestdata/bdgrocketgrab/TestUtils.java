package net.bestdata.bdgrocketgrab;

import net.bestdata.bdgrocketgrab.clients.responses.LeagueRiotResponse;
import net.bestdata.bdgrocketgrab.clients.responses.SummonerRiotResponse;
import net.bestdata.bdgrocketgrab.model.LeagueBySummoner;
import net.bestdata.bdgrocketgrab.model.Summoner;

public class TestUtils {
    public static LeagueRiotResponse[] createLeagueRiotResponse(String leagueId, String summonerId, String summonerName,
                                                                 String queueType, String tier, String rank,
                                                                 int leaguePoints, int wins, int losses, boolean hotStreak,
                                                                 boolean veteran, boolean freshBlood, boolean inactive) {
        LeagueRiotResponse leagueResponse = new LeagueRiotResponse();
        leagueResponse.setLeagueId(leagueId);
        leagueResponse.setSummonerId(summonerId);
        leagueResponse.setSummonerName(summonerName);
        leagueResponse.setQueueType(queueType);
        leagueResponse.setTier(tier);
        leagueResponse.setRank(rank);
        leagueResponse.setLeaguePoints(leaguePoints);
        leagueResponse.setWins(wins);
        leagueResponse.setLosses(losses);
        leagueResponse.setHotStreak(hotStreak);
        leagueResponse.setFreshBlood(veteran);
        leagueResponse.setVeteran(freshBlood);
        leagueResponse.setInactive(inactive);
        return new LeagueRiotResponse[]{leagueResponse};
    }

    public static SummonerRiotResponse createSummonerRiotResponse(String accountId, int profileIconId, long revisionDate,
                                                                  String name, String id, String puuid, long summonerLevel) {
        SummonerRiotResponse summonerRiotResponse = new SummonerRiotResponse();
        summonerRiotResponse.setAccountId(accountId);
        summonerRiotResponse.setProfileIconId(profileIconId);
        summonerRiotResponse.setRevisionDate(revisionDate);
        summonerRiotResponse.setName(name);
        summonerRiotResponse.setId(id);
        summonerRiotResponse.setPuuid(puuid);
        summonerRiotResponse.setSummonerLevel(summonerLevel);
        return summonerRiotResponse;
    }

    public static Summoner createSummoner(String accountId, int profileIconId, long revisionDate, String name, String id,
                                          String puuid, long summonerLevel, LeagueBySummoner leagueBySummoner) {
        Summoner summoner = new Summoner();
        summoner.setAccountId(accountId);
        summoner.setProfileIconId(profileIconId);
        summoner.setRevisionDate(revisionDate);
        summoner.setName(name);
        summoner.setId(id);
        summoner.setPuuid(puuid);
        summoner.setSummonerLevel(summonerLevel);
        summoner.setLeaguesBySummoners(new LeagueBySummoner[]{leagueBySummoner});
        return summoner;
    }

    public static LeagueBySummoner createLeagueBySummoner(String leagueId, String queueType, String tier, String rank,
                                                          int leaguePoints, int wins, int losses, boolean hotStreak,
                                                          boolean veteran, boolean freshBlood, boolean inactive) {
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
