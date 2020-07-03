package net.bestdata.game.rocketgrab.clients;

import net.bestdata.game.rocketgrab.clients.responses.MatchDetailRiotResponse;
import net.bestdata.game.rocketgrab.clients.responses.SummonerRiotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//TODO: header configuration 으로 분리
@FeignClient(name = "krRiotMatchClient", url = "${riot.api.asia.tft.url}", decode404 = true)
public interface KrRiotMatchClient {
    @GetMapping(value = "/match/v1/matches/by-puuid/{puuid}/ids", headers = "${riot.api.header.name}=${riot.api.key}")
    SummonerRiotResponse findMatchList(@PathVariable("puuid") String puuid);

    @GetMapping(value = "/match/v1/matches/{matchId}", headers = "${riot.api.header.name}=${riot.api.key}")
    Optional<MatchDetailRiotResponse> findMatchDetail(@PathVariable("matchId") String matchId);
}
