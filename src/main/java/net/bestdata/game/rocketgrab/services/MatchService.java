package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.clients.KrRiotMatchClient;
import net.bestdata.game.rocketgrab.clients.responses.MatchDetailRiotResponse;
import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.model.match.Participant;
import net.bestdata.game.rocketgrab.repositories.MatchRepository;
import net.bestdata.game.rocketgrab.repositories.documents.Match;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final KrRiotMatchClient krRiotMatchClient;
    private final SummonerService summonerService;

    public MatchService(MatchRepository matchRepository, KrRiotMatchClient krRiotMatchClient, SummonerService summonerService) {
        this.matchRepository = matchRepository;
        this.krRiotMatchClient = krRiotMatchClient;
        this.summonerService = summonerService;
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

    private Map<String, String> findSummonerNameByPuuid(List<Participant> participantList, Region region) {
        HashMap<String, String> result = new HashMap<>();
        participantList.forEach(participant -> {
            String puuid = participant.getPuuid();
            Optional<String> summonerName = summonerService.findSummonerNameByPuuid(puuid, region);
            summonerName.ifPresent(s -> result.put(puuid, s));
        });
        return result;
    }
}
