package net.bestdata.bdgrocketgrab.clients;

import net.bestdata.bdgrocketgrab.clients.responses.LeagueRiotResponse;
import net.bestdata.bdgrocketgrab.clients.responses.SummonerRiotResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RiotSummonerWebClient {
    @Value("${riot.api.tft.url}")
    private String riotApiUrl;
    @Value("${riot.api.header.name}")
    private String riotKeyHeaderName;
    @Value("${riot.api.key}")
    private String riotApiKey;

    @Value("${riot.api.tft.summoner.path}")
    private String summonerPathWithVariable;
    @Value("${riot.api.tft.league.path}")
    private String leaguePathWithVariable;


    public Mono<SummonerRiotResponse> findSummonerByName(String userName) {
        return WebClient.create(riotApiUrl).get()
                .uri(uriBuilder -> uriBuilder.path(summonerPathWithVariable).build(userName))
                .header(riotKeyHeaderName, riotApiKey)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(SummonerRiotResponse.class));
    }

    public Mono<LeagueRiotResponse[]> findLeagueBySummonerId(String summonerId) {
        return WebClient.create(riotApiUrl).get()
                .uri(uriBuilder -> uriBuilder.path(leaguePathWithVariable).build(summonerId))
                .header(riotKeyHeaderName, riotApiKey)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(LeagueRiotResponse[].class));
    }
}
