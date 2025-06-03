package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.domain.Roster;
import com.gathi.sch.schedulerAdmin.infra.ReduxClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class RosterService {

    private static final Logger logger = LoggerFactory.getLogger(RosterService.class);
    private final ReduxClient reduxClient;

    @Autowired
    public RosterService(ReduxClient reduxClient) {
        this.reduxClient = reduxClient;
    }


    @CircuitBreaker(name = "roster-service", fallbackMethod = "serviceTempUnavailable")
    @Bulkhead(name = "getRostersBH", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "tooManyConcurrentRequests")
    public Flux<Roster> getAllRosters() {
        return reduxClient.getAllRosters();
    }

    public Flux<Roster> serviceTempUnavailable(Throwable t) {
        logger.error("Service temporarily unavailable", t);
        return Flux.error(new ServiceUnavailableException("Service temporarily unavailable"));
    }

    public Flux<Roster> tooManyConcurrentRequests(Throwable t) {
        logger.error("Too many concurrent requests", t);
        return Flux.error(new ServiceUnavailableException("Too many concurrent requests"));
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
