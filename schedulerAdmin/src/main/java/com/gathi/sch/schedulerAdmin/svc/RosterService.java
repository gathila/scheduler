package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.domain.Roster;
import com.gathi.sch.schedulerAdmin.infra.ReduxClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class RosterService {


    private final ReduxClient reduxClient;

    @Autowired
    public RosterService(ReduxClient reduxClient) {
        this.reduxClient = reduxClient;
    }


    @CircuitBreaker(name = "SERVICE_NAME", fallbackMethod = "getDefaultRosters")
    public List<Roster> getAllRosters() {
        Mono<List<Roster>> listMono = reduxClient.getAllRosters()
                .cache()
                .collectList();
        return listMono.block();
    }

    public List<Roster> getDefaultRosters(Throwable t) {
        System.out.println(t.getMessage());
        return Collections.emptyList();
    }


    public Flux<Integer> generateNumbers() {
        Random r = new Random();
        return Flux.generate(sink -> {
            int num = r.nextInt(100);
            sink.next(num);
            System.out.println("GENERATED " + num);
            System.out.println("THREAD_IN_PRODUCER: " + Thread.currentThread().getName());

            if (num > 90) {
                sink.complete();
            }
        });
    }
}
