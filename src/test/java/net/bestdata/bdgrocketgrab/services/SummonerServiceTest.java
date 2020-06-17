package net.bestdata.bdgrocketgrab.services;

import net.bestdata.bdgrocketgrab.TestUtils;
import net.bestdata.bdgrocketgrab.clients.RiotSummonerWebClient;
import net.bestdata.bdgrocketgrab.clients.responses.LeagueRiotResponse;
import net.bestdata.bdgrocketgrab.clients.responses.SummonerRiotResponse;
import net.bestdata.bdgrocketgrab.model.Summoner;
import net.bestdata.bdgrocketgrab.repositories.SummonerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class SummonerServiceTest {
    private SummonerService summonerService;
    private SummonerRepository summonerRepository;
    private RiotSummonerWebClient riotSummonerWebClient;

    @BeforeEach
    public void setup() {
        summonerRepository = mock(SummonerRepository.class);
        riotSummonerWebClient = mock(RiotSummonerWebClient.class);
        summonerService = new SummonerService(summonerRepository, riotSummonerWebClient);
    }

    @Test
    public void test_summonerService_findSummoner_success_when_has_in_database() {
        Summoner mockSummoner = TestUtils.createSummoner("mockAccountId", 1, 234234, "mockName", "mockId", "mockPuuid", 3,
                TestUtils.createLeagueBySummoner("mockLeagueId", "mockQueueType", "mockTier", "mockRank", 0, 0, 0, false, false, false, false));

        when(summonerRepository.findByName("mockName")).thenReturn(Mono.just(mockSummoner));
        when(riotSummonerWebClient.findSummonerByName("mockName")).thenReturn(Mono.empty());
        when(riotSummonerWebClient.findLeagueBySummonerId("mockId")).thenReturn(Mono.empty());


        Mono<Summoner> result = summonerService.findSummonerOrFindAndSaveIfEmptyInRepository("mockName");

        StepVerifier.create(result)
                .assertNext(summoner -> {
                    assertEquals(summoner.getAccountId(), "mockAccountId");
                    assertEquals(summoner.getProfileIconId(), 1);
                    assertEquals(summoner.getRevisionDate(), 234234);
                    assertEquals(summoner.getName(), "mockName");
                    assertEquals(summoner.getId(), "mockId");
                    assertEquals(summoner.getPuuid(), "mockPuuid");
                    assertEquals(summoner.getSummonerLevel(), 3);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLeagueId(), "mockLeagueId");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getQueueType(), "mockQueueType");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getTier(), "mockTier");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getRank(), "mockRank");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLeaguePoints(), 0);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getWins(), 0);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLosses(), 0);
                    assertFalse(summoner.getLeaguesBySummoners()[0].isHotStreak());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isVeteran());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isFreshBlood());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isInactive());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void test_summonerService_findSummoner_success_when_not_has_in_database() {
        SummonerRiotResponse summonerRiotResponse = TestUtils.createSummonerRiotResponse("mockAccountId", 1, 234234, "mockName", "mockId", "mockPuuid", 3);
        LeagueRiotResponse[] leagueRiotResponse = TestUtils.createLeagueRiotResponse("mockLeagueId", "mockId", "mockSummonerName",
                "mockQueueType", "mockTier", "mockRank", 0, 0, 0, false, false, false, false);
        when(summonerRepository.findByName("mockName")).thenReturn(Mono.empty());
        when(riotSummonerWebClient.findSummonerByName("mockName")).thenReturn(Mono.just(summonerRiotResponse));
        when(riotSummonerWebClient.findLeagueBySummonerId("mockId")).thenReturn(Mono.just(leagueRiotResponse));

        Summoner expectSummoner = TestUtils.createSummoner("mockAccountId", 1, 234234, "mockName", "mockId", "mockPuuid", 3,
                TestUtils.createLeagueBySummoner("mockLeagueId", "mockQueueType", "mockTier", "mockRank", 0, 0, 0, false, false, false, false));
        when(summonerRepository.save(expectSummoner)).thenReturn(Mono.just(expectSummoner));


        Mono<Summoner> result = summonerService.findSummonerOrFindAndSaveIfEmptyInRepository("mockName");

        StepVerifier.create(result)
                .assertNext(summoner -> {
                    assertEquals(summoner.getAccountId(), "mockAccountId");
                    assertEquals(summoner.getProfileIconId(), 1);
                    assertEquals(summoner.getRevisionDate(), 234234);
                    assertEquals(summoner.getName(), "mockName");
                    assertEquals(summoner.getId(), "mockId");
                    assertEquals(summoner.getPuuid(), "mockPuuid");
                    assertEquals(summoner.getSummonerLevel(), 3);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLeagueId(), "mockLeagueId");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getQueueType(), "mockQueueType");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getTier(), "mockTier");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getRank(), "mockRank");
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLeaguePoints(), 0);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getWins(), 0);
                    assertEquals(summoner.getLeaguesBySummoners()[0].getLosses(), 0);
                    assertFalse(summoner.getLeaguesBySummoners()[0].isHotStreak());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isVeteran());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isFreshBlood());
                    assertFalse(summoner.getLeaguesBySummoners()[0].isInactive());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void test_summonerService_findSummoner_fail_when_no_userName() {
        when(summonerRepository.findByName("mockName")).thenReturn(Mono.empty());
        when(riotSummonerWebClient.findSummonerByName("mockName")).thenReturn(Mono.empty());
        when(riotSummonerWebClient.findLeagueBySummonerId("mockId")).thenReturn(Mono.empty());


        Mono<Summoner> result = summonerService.findSummonerOrFindAndSaveIfEmptyInRepository("mockName");

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
}