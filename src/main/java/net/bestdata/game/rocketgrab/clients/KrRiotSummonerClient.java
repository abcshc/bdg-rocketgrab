package net.bestdata.game.rocketgrab.clients;

import net.bestdata.game.rocketgrab.clients.responses.SummonerRiotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//TODO: header configuration 으로 분리
@FeignClient(name = "krRiotSummonerClient", url = "${riot.api.kr.tft.url}", decode404 = true)
public interface KrRiotSummonerClient {
    @GetMapping(value = "/summoner/v1/summoners/by-name/{userName}", headers = "${riot.api.header.name}=${riot.api.key}")
    Optional<SummonerRiotResponse> findSummonerByUserName(@PathVariable("userName") String userName);

    @GetMapping(value = "/summoner/v1/summoners/by-puuid/{puuid}", headers = "${riot.api.header.name}=${riot.api.key}")
    Optional<SummonerRiotResponse> findSummonerByPuuid(@PathVariable("puuid") String puuid);
}
