package net.bestdata.game.rocketgrab.clients.responses.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantDto {
    private CompanionDto companion;
    private int gold_left;
    private int last_round;
    private int level;
    private int placement;
    private int players_eliminated;
    private String puuid;
    private float time_eliminated;
    private int total_damage_to_players;
    private TraitDto[] traits;
    private UnitDto[] units;
}
