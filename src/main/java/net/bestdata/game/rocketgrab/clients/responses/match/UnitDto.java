package net.bestdata.game.rocketgrab.clients.responses.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto {
    private int[] items;
    private String character_id;
    private String name;
    private int rarity;
    private int tier;
}
