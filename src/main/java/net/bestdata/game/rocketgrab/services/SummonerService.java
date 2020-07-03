package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.clients.KrRiotSummonerClient;
import net.bestdata.game.rocketgrab.clients.responses.SummonerRiotResponse;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.SummonerRepository;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final KrRiotSummonerClient krRiotSummonerClient;

    public SummonerService(SummonerRepository summonerRepository, KrRiotSummonerClient krRiotSummonerClient) {
        this.summonerRepository = summonerRepository;
        this.krRiotSummonerClient = krRiotSummonerClient;
    }

    public Optional<Summoner> find(String summonerName, Region region) {
        return summonerRepository.findBySummonerNameAndRegion(summonerName, region);
    }

    // TODO: 지역 별 분기
    public Optional<Summoner> refresh(String summonerName, Region region) {
        Summoner summoner = summonerRepository.findBySummonerNameAndRegion(summonerName, region).orElse(Summoner.newEmpty());
        Optional<SummonerRiotResponse> clientSummoner = krRiotSummonerClient.findSummonerByUserName(summonerName);
        if (clientSummoner.isEmpty()) {
            return Optional.empty();
        }
        summoner.updateViaSummonerApi(clientSummoner.get(), region);

        return Optional.of(summonerRepository.save(summoner));
    }

    public Optional<String> findSummonerNameByPuuid(String puuid, Region region) {
        Optional<Summoner> repositorySummoner = summonerRepository.findByPuuid(puuid);
        if (repositorySummoner.isPresent()) {
            return repositorySummoner.map(Summoner::getSummonerName);
        }
        Optional<SummonerRiotResponse> response = krRiotSummonerClient.findSummonerByPuuid(puuid);

        if (response.isEmpty()) {
            return Optional.empty();
        }

        return response.map(summonerRiotResponse -> {
            Summoner summoner = Summoner.newEmpty();
            summoner.updateViaSummonerApi(summonerRiotResponse, region);
            return summonerRepository.save(summoner).getSummonerName();
        });
    }
}
