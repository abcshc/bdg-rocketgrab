package net.bestdata.game.rocketgrab.repositories;

import net.bestdata.game.rocketgrab.model.enums.Region;
import net.bestdata.game.rocketgrab.repositories.documents.Summoner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerRepository extends MongoRepository<Summoner, String> {
    Summoner findByNameAndRegion(String summonerName, Region region);
}
