package net.bestdata.bdgrocketgrab.routers;

import net.bestdata.bdgrocketgrab.services.SummonerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class SummonerRouter {
    private final SummonerService summonerService;

    public SummonerRouter(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @Bean
    public RouterFunction<ServerResponse> routeSummoner() {
        return RouterFunctions
                .route(GET("/summoner/{summonerName}"),
                        request -> {
                            String summonerName = request.pathVariable("summonerName");
                            return summonerService.findSummonerOrFindAndSaveIfEmptyInRepository(summonerName)
                                    .flatMap(summoner -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(summoner))
                                    .switchIfEmpty(ServerResponse.notFound().build());
                        });
    }
}
