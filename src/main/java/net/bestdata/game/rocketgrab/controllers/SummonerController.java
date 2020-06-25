package net.bestdata.game.rocketgrab.controllers;

import net.bestdata.game.rocketgrab.controllers.response.CurrentSeason;
import net.bestdata.game.rocketgrab.controllers.response.SummonerResponse;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import net.bestdata.game.rocketgrab.services.SummonerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummonerController {
    @Value("${riot.tft.current.version}")
    private String currentSeason;

    private final SummonerService summonerService;

    public SummonerController(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    // TODO: Not Found 처리
    @GetMapping("/summoner/{region}/{summonerName}")
    public SummonerResponse getSummoner(@PathVariable(value = "region") String region,
                                        @PathVariable(value = "summonerName") String summonerName) {
        Summoner summoner = summonerService.findByNameAndRegion(summonerName, Region.valueOf(region));
        return convertSummonerResponse(summoner, currentSeason);
    }

    private SummonerResponse convertSummonerResponse(Summoner summoner, String currentSeason) {
        return new SummonerResponse(summoner.getSummonerName(), summoner.getProfileIconId(), summoner.getSummonerLevel(),
                CurrentSeason.newBySeasonStatistic(summoner.getSeasonStatistic(currentSeason)));
    }
}
