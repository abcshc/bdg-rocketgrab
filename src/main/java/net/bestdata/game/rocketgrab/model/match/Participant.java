package net.bestdata.game.rocketgrab.model.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.match.ParticipantDto;

@AllArgsConstructor
@Getter
public class Participant {
    private String puuid;
    private LittleLegend littleLegend;
    private Composition composition;
    private int goldLeft;
    private int lastRound;
    private int level;
    private int placement;
    private int playersEliminated;
    private float timeEliminated;
    private int totalDamageToPlayers;
    private String summonerName;

    public static Participant newByParticipantDto(ParticipantDto participantDto) {
        return new Participant(participantDto.getPuuid(),
                LittleLegend.newByCompanionDto(participantDto.getCompanion()),
                Composition.newByParticipantDto(participantDto),
                participantDto.getGold_left(),
                participantDto.getLast_round(),
                participantDto.getLevel(),
                participantDto.getPlacement(),
                participantDto.getPlayers_eliminated(),
                participantDto.getTime_eliminated(),
                participantDto.getTotal_damage_to_players(),
                "");
    }

    public void updateSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }
}
