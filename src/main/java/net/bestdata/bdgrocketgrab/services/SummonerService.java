package net.bestdata.bdgrocketgrab.services;

import net.bestdata.bdgrocketgrab.clients.RiotSummonerWebClient;
import net.bestdata.bdgrocketgrab.model.LeagueBySummoner;
import net.bestdata.bdgrocketgrab.model.Summoner;
import net.bestdata.bdgrocketgrab.repositories.SummonerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final RiotSummonerWebClient riotSummonerWebClient;

    public SummonerService(SummonerRepository summonerRepository, RiotSummonerWebClient riotSummonerWebClient) {
        this.summonerRepository = summonerRepository;
        this.riotSummonerWebClient = riotSummonerWebClient;
    }

    // TODO: 사용자 이름 띄어쓰기 처리
    public Mono<Summoner> findSummonerOrFindAndSaveIfEmptyInRepository(String name) {
        Mono<Summoner> repositoryMono = summonerRepository.findByName(name);
        Mono<Summoner> apiMono = findSummonerByNameViaApi(name)
                .flatMap(summoner -> summonerRepository.save(summoner));

        return repositoryMono.switchIfEmpty(apiMono);
    }

    private Mono<Summoner> findSummonerByNameViaApi(String name) {
        return riotSummonerWebClient.findSummonerByName(name)
                .flatMap(summonerRiotResponse -> {
                    Summoner summoner = summonerRiotResponse.convertToSummoner();
                    return riotSummonerWebClient.findLeagueBySummonerId(summoner.getId())
                            .flatMap(leagueRiotResponses -> {
                                LeagueBySummoner[] leagueBySummoners = new LeagueBySummoner[leagueRiotResponses.length];
                                for (int i = 0; i < leagueRiotResponses.length; i++) {
                                    leagueBySummoners[i] = leagueRiotResponses[i].convertToLeagueBySummoner();
                                }
                                summoner.setLeaguesBySummoners(leagueBySummoners);
                                return Mono.just(summoner);
                            });
                });
    }
}
