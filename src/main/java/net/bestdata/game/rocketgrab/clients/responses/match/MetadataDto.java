package net.bestdata.game.rocketgrab.clients.responses.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataDto {
    private String data_version;
    private String match_id;
    private String[] participants;
}
