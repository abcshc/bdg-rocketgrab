package net.bestdata.game.rocketgrab.listeners;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RiotRequest {
    private String requestType;
    private String keyword;
    private String region;
}
