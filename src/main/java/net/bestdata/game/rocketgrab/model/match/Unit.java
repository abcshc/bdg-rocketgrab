package net.bestdata.game.rocketgrab.model.match;

import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.match.UnitDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public class Unit {
    private String characterId;
    private List<Integer> items;
    private String name;
    private int rarity;
    private int tier;

    public static Unit newByUnitDto(UnitDto unitDto) {
        return new Unit(unitDto.getCharacter_id(), Ints.asList(unitDto.getItems()), unitDto.getName(), unitDto.getRarity(), unitDto.getTier());
    }

    public static List<Unit> newListByUnitDtos(UnitDto[] unitDtos) {
        return Stream.of(unitDtos).map(Unit::newByUnitDto).collect(Collectors.toList());
    }
}