package net.bestdata.game.rocketgrab.clients.responses;

import lombok.Getter;
import lombok.Setter;
import net.bestdata.game.rocketgrab.clients.responses.match.InfoDto;
import net.bestdata.game.rocketgrab.clients.responses.match.MetadataDto;

// MatchDTO
@Getter
@Setter
public class MatchDetailRiotResponse {
    private MetadataDto metadata;
    private InfoDto info;
}
