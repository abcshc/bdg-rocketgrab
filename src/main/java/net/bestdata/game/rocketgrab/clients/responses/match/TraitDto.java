package net.bestdata.game.rocketgrab.clients.responses.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraitDto {
    private String name;
    private int num_units;
    private int tier_current;
    private int tier_total;
}
