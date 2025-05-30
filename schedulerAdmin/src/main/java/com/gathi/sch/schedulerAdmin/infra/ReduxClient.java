package com.gathi.sch.schedulerAdmin.infra;

import com.gathi.sch.schedulerAdmin.domain.Roster;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ReduxClient {

    private WebClient webClient;

    @Autowired
    public ReduxClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Roster> getAllRosters() {
        return webClient.get()
                .uri("/roster/all")
                .retrieve()
                .bodyToFlux(Roster.class);
    }
}
