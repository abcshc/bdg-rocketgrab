package net.bestdata.game.rocketgrab.services;

import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.SummonerRepository;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import org.springframework.stereotype.Service;

@Service
public class SummonerService {
    private final SummonerRepository summonerRepository;

    public SummonerService(SummonerRepository summonerRepository) {
        this.summonerRepository = summonerRepository;
    }

    public Summoner findByNameAndRegion(String summonerName, Region region) {
        return summonerRepository.findByNameAndRegion(summonerName, region);
    }
}
