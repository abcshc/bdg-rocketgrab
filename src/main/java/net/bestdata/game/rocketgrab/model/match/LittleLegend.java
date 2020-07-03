package net.bestdata.game.rocketgrab.model.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.match.CompanionDto;

@AllArgsConstructor
@Getter
public class LittleLegend {
    private String contentId;
    private int skinId;
    private String species;

    public static LittleLegend newByCompanionDto(CompanionDto companionDto) {
        return new LittleLegend(companionDto.getContent_ID(), companionDto.getSkin_ID(), companionDto.getSpecies());
    }
}