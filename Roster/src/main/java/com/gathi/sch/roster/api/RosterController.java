package com.gathi.sch.roster.api;

import com.gathi.sch.roster.dom.model.Roster;
import com.gathi.sch.roster.svc.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/roster")
public class RosterController {

    private final RosterService rosterService;

    @Autowired
    public RosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<Roster>> getAll() {
        Flux<Roster> all = this.rosterService
                .findAll()
                .doOnSubscribe(sub -> System.out.println("Subscribed to findAll"))
                .doOnNext(roster -> System.out.println("Fetched roster: " + roster));
        return ResponseEntity.ok(all);
    }
}
