package net.bestdata.game.rocketgrab.model.summoner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.model.match.Composition;
import net.bestdata.game.rocketgrab.model.match.Trait;
import net.bestdata.game.rocketgrab.model.match.Unit;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class MyMatch {
    private String dataVersion;
    private LocalDateTime gameDateTime;
    private float gameLength;
    private String gameVariation;

    private List<Unit> units;
    private List<Trait> traits;
    private List<ShortParticipant> shortParticipants;

    public static MyMatch newMyMatch(String dataVersion, LocalDateTime gameDateTime, float gameLength, String gameVariation, Composition composition, List<ShortParticipant> shortParticipants) {
        return new MyMatch(dataVersion, gameDateTime, gameLength, gameVariation, composition.getUnits(), composition.getTraits(), shortParticipants);
    }
}
