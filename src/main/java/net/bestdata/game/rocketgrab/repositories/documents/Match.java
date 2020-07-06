package net.bestdata.game.rocketgrab.repositories.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.MatchDetailRiotResponse;
import net.bestdata.game.rocketgrab.model.match.Participant;
import net.bestdata.game.rocketgrab.model.summoner.MyMatch;
import net.bestdata.game.rocketgrab.model.summoner.ShortParticipant;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document(collection = "match")
@AllArgsConstructor
@Getter
public class Match {
    private String id;
    private String dataVersion;
    private LocalDateTime gameDateTime;
    private float gameLength;
    private String gameVariation;
    private List<Participant> participants;
    private List<ShortParticipant> shortParticipants;

    public static Match newByMatchDetailRiotResponse(MatchDetailRiotResponse matchDetailRiotResponse) {
        List<Participant> participants = Stream.of(matchDetailRiotResponse.getInfo().getParticipants()).map(Participant::newByParticipantDto)
                .sorted(Comparator.comparingInt(Participant::getPlacement)).collect(Collectors.toList());
        return new Match(matchDetailRiotResponse.getMetadata().getMatch_id(),
                matchDetailRiotResponse.getMetadata().getData_version(),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(matchDetailRiotResponse.getInfo().getGame_datetime()), TimeZone.getDefault().toZoneId()),
                matchDetailRiotResponse.getInfo().getGame_length(),
                matchDetailRiotResponse.getInfo().getGame_variation(),
                participants,
                Collections.emptyList());
    }

    public void updateSummonerNameAndGenerateShortParticipant(Map<String, String> summonerMap) {
        updateParticipantsSummonerName(summonerMap);
        generateShortParticipant();
    }

    private void updateParticipantsSummonerName(Map<String, String> summonerMap) {
        for (Participant participant : participants) {
            String name = summonerMap.get(participant.getPuuid());
            if (name == null) {
                participant.updateSummonerName("!알 수 없는 소환사");
            } else {
                participant.updateSummonerName(name);
            }
        }
    }

    private void generateShortParticipant() {
        shortParticipants = participants.stream()
                .map(participant -> new ShortParticipant(participant.getSummonerName(), participant.getLittleLegend())).collect(Collectors.toList());
    }

    public Optional<MyMatch> extractMyMatch(String puuid) {
        return participants.stream()
                .filter(participant -> puuid.equals(participant.getPuuid()))
                .findFirst()
                .map(participant -> MyMatch.newMyMatch(dataVersion, gameDateTime, gameLength, gameVariation, participant.getComposition(), shortParticipants, participant.getPlacement()));
    }
}
