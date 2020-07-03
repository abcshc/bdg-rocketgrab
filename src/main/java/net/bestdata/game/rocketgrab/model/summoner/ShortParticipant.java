package net.bestdata.game.rocketgrab.model.summoner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.model.match.LittleLegend;

@AllArgsConstructor
@Getter
public class ShortParticipant {
    private String summonerName;
    private LittleLegend littleLegend;
}
