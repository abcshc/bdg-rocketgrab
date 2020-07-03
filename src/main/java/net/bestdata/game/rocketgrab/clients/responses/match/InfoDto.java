package net.bestdata.game.rocketgrab.clients.responses.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoDto {
    private long game_datetime;
    private float game_length;
    private String game_variation;
    private String game_version;
    private ParticipantDto[] participants;
    private int queue_id;
    private int tft_set_number;
}
