package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.clients.KrRiotMatchClient;
import net.bestdata.game.rocketgrab.clients.KrRiotSummonerClient;
import net.bestdata.game.rocketgrab.clients.responses.MatchDetailRiotResponse;
import net.bestdata.game.rocketgrab.clients.responses.SummonerRiotResponse;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.model.match.Participant;
import net.bestdata.game.rocketgrab.repositories.MatchRepository;
import net.bestdata.game.rocketgrab.repositories.SummonerRepository;
import net.bestdata.game.rocketgrab.repositories.documents.Match;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//TODO: MatchService, SummonerService 명확히 역할 구분하기
@Service
public class SummonerDataService {
    private final SummonerRepository summonerRepository;
    private final KrRiotSummonerClient krRiotSummonerClient;
    private final MatchRepository matchRepository;
    private final KrRiotMatchClient krRiotMatchClient;

    public SummonerDataService(SummonerRepository summonerRepository, KrRiotSummonerClient krRiotSummonerClient, MatchRepository matchRepository, KrRiotMatchClient krRiotMatchClient) {
        this.summonerRepository = summonerRepository;
        this.krRiotSummonerClient = krRiotSummonerClient;
        this.matchRepository = matchRepository;
        this.krRiotMatchClient = krRiotMatchClient;
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


    public Optional<Match> findAndSaveMatch(String matchId, Region region) {
        Optional<Match> repositoryMatch = matchRepository.findById(matchId);
        if (repositoryMatch.isPresent()) {
            return repositoryMatch;
        } else {
            Optional<MatchDetailRiotResponse> clientResponse = krRiotMatchClient.findMatchDetail(matchId);
            if (clientResponse.isEmpty()) {
                return Optional.empty();
            }

            Match match = Match.newByMatchDetailRiotResponse(clientResponse.get());
            match.updateSummonerNameAndGenerateShortParticipant(findSummonerNameByPuuid(match.getParticipants(), region));

            return Optional.of(matchRepository.save(match));
        }
    }

    public Map<String, String> findSummonerNameByPuuid(List<Participant> participantList, Region region) {
        HashMap<String, String> result = new HashMap<>();
        participantList.forEach(participant -> {
            String puuid = participant.getPuuid();
            Optional<String> summonerName = findSummonerNameByPuuid(puuid, region);
            summonerName.ifPresent(s -> result.put(puuid, s));
        });
        return result;
    }
}
