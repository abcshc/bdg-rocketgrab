package net.bestdata.game.rocketgrab.repositories;

import net.bestdata.game.rocketgrab.repositories.documents.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MatchRepository extends MongoRepository<Match, String> {
    Optional<Match> findById(String matchId);
}
