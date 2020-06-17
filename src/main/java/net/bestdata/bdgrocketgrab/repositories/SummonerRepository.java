package net.bestdata.bdgrocketgrab.repositories;

import net.bestdata.bdgrocketgrab.model.Summoner;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SummonerRepository extends ReactiveCrudRepository<Summoner, String> {
    Mono<Summoner> findByName(String name);
}
