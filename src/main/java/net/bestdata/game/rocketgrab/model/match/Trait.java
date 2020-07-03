package net.bestdata.game.rocketgrab.model.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.match.TraitDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public class Trait {
    private String name;
    private int numUnits;
    private int tierCurrent;
    private int tierTotal;

    public static Trait newByTraitDto(TraitDto traitDto) {
        return new Trait(traitDto.getName(), traitDto.getNum_units(), traitDto.getTier_current(), traitDto.getTier_total());
    }

    public static List<Trait> newListByTraitDtos(TraitDto[] traitDtos) {
        return Stream.of(traitDtos).map(Trait::newByTraitDto).collect(Collectors.toList());
    }
}
