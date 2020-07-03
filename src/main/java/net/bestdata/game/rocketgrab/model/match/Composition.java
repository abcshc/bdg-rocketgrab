package net.bestdata.game.rocketgrab.model.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bestdata.game.rocketgrab.clients.responses.match.ParticipantDto;

import java.util.List;

@AllArgsConstructor
@Getter
public class Composition {
    private List<Trait> traits;
    private List<Unit> units;

    public static Composition newByParticipantDto(ParticipantDto participantDto) {
        return new Composition(Trait.newListByTraitDtos(participantDto.getTraits()),
                Unit.newListByUnitDtos(participantDto.getUnits()));
    }
}
