package net.bestdata.game.rocketgrab.repositories.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.model.summoner.MyMatch;
import net.bestdata.game.rocketgrab.model.summoner.SeasonStatistic;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "summoner")
@CompoundIndexes({
        @CompoundIndex(def = "{'region' : 1, 'summonerName': 1}", name = "summoner_name_index", unique = true)
})
@AllArgsConstructor
@Getter
public class Summoner {
    private String summonerName;
    private Region region;

    private String accountId;
    private String riotId;
    private String puuid;
    private List<MyMatch> myMatches;
    private Map<String, SeasonStatistic> seasonStatisticMap;

    private int profileIconId;
    private long summonerLevel;

    // TODO: 없는 정보 처리
    public SeasonStatistic getSeasonStatistic(String season) {
        return seasonStatisticMap.get(season);
    }
}
