package net.bestdata.bdgrocketgrab.routers;

import net.bestdata.bdgrocketgrab.TestUtils;
import net.bestdata.bdgrocketgrab.model.Summoner;
import net.bestdata.bdgrocketgrab.services.SummonerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SummonerRouterTest {
    private WebTestClient client;

    private SummonerService summonerService;

    @BeforeEach
    public void setup() {
        summonerService = mock(SummonerService.class);
        SummonerRouter summonerRouter = new SummonerRouter(summonerService);
        client = WebTestClient.bindToRouterFunction(summonerRouter.routeSummoner()).build();
    }

    @Test
    public void test_routeSummoner_respond_ok_when_findSummoner() {
        Summoner mockSummoner = TestUtils.createSummoner("mockAccountId", 1, 234234, "mockName", "mockId", "mockPuuid", 3,
                TestUtils.createLeagueBySummoner("mockLeagueId", "mockQueueType", "mockTier", "mockRank", 0, 0, 0, false, false, false, false));
        when(summonerService.findSummonerOrFindAndSaveIfEmptyInRepository("mockName")).thenReturn(Mono.just(mockSummoner));
        client.get().uri("/summoner/mockName")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.accountId").isEqualTo("mockAccountId")
                .jsonPath("$.profileIconId").isEqualTo(1)
                .jsonPath("$.revisionDate").isEqualTo(234234)
                .jsonPath("$.name").isEqualTo("mockName")
                .jsonPath("$.id").isEqualTo("mockId")
                .jsonPath("$.puuid").isEqualTo("mockPuuid")
                .jsonPath("$.summonerLevel").isEqualTo(3)
                .jsonPath("$.leaguesBySummoners[0].leagueId").isEqualTo("mockLeagueId")
                .jsonPath("$.leaguesBySummoners[0].queueType").isEqualTo("mockQueueType")
                .jsonPath("$.leaguesBySummoners[0].tier").isEqualTo("mockTier")
                .jsonPath("$.leaguesBySummoners[0].rank").isEqualTo("mockRank")
                .jsonPath("$.leaguesBySummoners[0].leaguePoints").isEqualTo(0)
                .jsonPath("$.leaguesBySummoners[0].wins").isEqualTo(0)
                .jsonPath("$.leaguesBySummoners[0].losses").isEqualTo(0)
                .jsonPath("$.leaguesBySummoners[0].hotStreak").isEqualTo(false)
                .jsonPath("$.leaguesBySummoners[0].veteran").isEqualTo(false)
                .jsonPath("$.leaguesBySummoners[0].freshBlood").isEqualTo(false)
                .jsonPath("$.leaguesBySummoners[0].inactive").isEqualTo(false);
    }

    @Test
    public void test_routeSummoner_respond_notFound_when_findSummoner_isEmpty() {
        when(summonerService.findSummonerOrFindAndSaveIfEmptyInRepository("mockName")).thenReturn(Mono.empty());
        client.get().uri("/summoner/mockName")
                .exchange()
                .expectStatus().isNotFound();
    }
}