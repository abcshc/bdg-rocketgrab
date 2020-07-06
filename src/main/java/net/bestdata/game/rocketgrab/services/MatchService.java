package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.clients.KrRiotMatchClient;
import net.bestdata.game.rocketgrab.repositories.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatchService {
    private final KrRiotMatchClient krRiotMatchClient;

    public MatchService(MatchRepository matchRepository, KrRiotMatchClient krRiotMatchClient) {
        this.krRiotMatchClient = krRiotMatchClient;
    }

    public List<String> findMatchHistory(String puuid) {
        return Stream.of(krRiotMatchClient.findMatchList(puuid, 2000)).collect(Collectors.toList());
    }
}
