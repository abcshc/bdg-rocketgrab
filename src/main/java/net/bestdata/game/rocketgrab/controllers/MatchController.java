package net.bestdata.game.rocketgrab.controllers;

import net.bestdata.game.rocketgrab.exception.HttpNotFoundException;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import net.bestdata.game.rocketgrab.services.MatchService;
import net.bestdata.game.rocketgrab.services.SummonerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchController {
    private final MatchService matchService;
    private final SummonerService summonerService;

    public MatchController(MatchService matchService, SummonerService summonerService) {
        this.matchService = matchService;
        this.summonerService = summonerService;
    }

    @GetMapping("/match/history/{summonerName}")
    public List<String> getMatchList(@PathVariable(value = "summonerName") String summonerName) {
        Summoner summoner = summonerService.find(summonerName, Region.KR).orElseThrow(HttpNotFoundException::new);
        return matchService.findMatchHistory(summoner.getPuuid());
    }
}
