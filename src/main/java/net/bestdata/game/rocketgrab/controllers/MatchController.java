package net.bestdata.game.rocketgrab.controllers;

import net.bestdata.game.rocketgrab.exception.HttpNotFoundException;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.documents.Match;
import net.bestdata.game.rocketgrab.services.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // TODO: Not Found 처리, 필요하지 않은 API 테스트 후 제거 필요.
    // TODO: matchId 별 Region 처리
    @GetMapping("/match/{matchId}")
    public Match getSummoner(@PathVariable(value = "matchId") String matchId) {
        return matchService.findAndSaveMatch(matchId, Region.KR).orElseThrow(HttpNotFoundException::new);
    }
}
