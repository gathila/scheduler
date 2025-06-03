package com.gathi.sch.schedulerAdmin.api;

import com.gathi.sch.schedulerAdmin.domain.Roster;
import com.gathi.sch.schedulerAdmin.svc.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roster")
public class RosterController {

    private final RosterService rosterService;

    @Autowired
    public RosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<Roster>> getAllRosters() {
        System.out.println("THREAD: " + Thread.currentThread().getName());
        this.rosterService.generateNumbers()
                .filter(e -> e > 40)
                .collectMap(e -> e)
                .subscribe(
                        e -> {
                            System.out.println("DATA: " + e);
                            System.out.println("THREAD_IN_CONSUMER: " + Thread.currentThread().getName());
                        },
                        throwable -> System.out.println("ERROR: " + throwable.getMessage()),
                        () -> System.out.println("Completed")
                );
        return ResponseEntity.ok(this.rosterService.getAllRosters());
    }
}
