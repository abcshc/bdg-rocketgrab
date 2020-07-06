package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.clients.KrRiotMatchClient;
import net.bestdata.game.rocketgrab.clients.KrRiotSummonerClient;
import net.bestdata.game.rocketgrab.clients.responses.SummonerRiotResponse;
import net.bestdata.game.rocketgrab.exception.InvalidMyMatchException;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.SummonerRepository;
import net.bestdata.game.rocketgrab.repositories.documents.Match;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final KrRiotSummonerClient krRiotSummonerClient;
    private final KrRiotMatchClient krRiotMatchClient;
    private final SummonerDataService summonerDataService;

    public SummonerService(SummonerRepository summonerRepository, KrRiotSummonerClient krRiotSummonerClient, KrRiotMatchClient krRiotMatchClient, SummonerDataService summonerDataService) {
        this.summonerRepository = summonerRepository;
        this.krRiotSummonerClient = krRiotSummonerClient;
        this.krRiotMatchClient = krRiotMatchClient;
        this.summonerDataService = summonerDataService;
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

        String puuid = summoner.getPuuid();
        // TODO: count 숨기기
        String[] matchIds = krRiotMatchClient.findMatchList(puuid, 2000);

        for (String matchId : matchIds) {
            if(summoner.isCheckedMatch(matchId)) {
                break;
            }
            Optional<Match> match = summonerDataService.findAndSaveMatch(matchId, region);
            match.ifPresent(it -> summoner.addMyMatch(it.extractMyMatch(puuid).orElseThrow(InvalidMyMatchException::new)));
        }
        // TODO: Match 정보 갱신
        // 2. MyMatch에 추가된 데이터가 있는 경우 해당 데이터 시즌의 통계 데이터 생성 후 저장

        return Optional.of(summonerRepository.save(summoner));
    }

    public Optional<String> findPuuidBySummonerId(String summonerName, Region region) {
        Summoner summoner = find(summonerName, region).orElse(Summoner.newEmpty());
        Optional<SummonerRiotResponse> clientSummoner = krRiotSummonerClient.findSummonerByUserName(summonerName);
        if (clientSummoner.isEmpty()) {
            return Optional.empty();
        }
        summoner.updateViaSummonerApi(clientSummoner.get(), region);
        summonerRepository.save(summoner);
        return Optional.of(summoner.getSummonerName());
    }
}
