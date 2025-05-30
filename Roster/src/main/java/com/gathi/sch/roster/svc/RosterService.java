package com.gathi.sch.roster.svc;

import com.gathi.sch.roster.dom.model.Roster;
import com.gathi.sch.roster.dao.RosterRepositoryImpl;
import com.gathi.sch.roster.dom.repo.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RosterService {

    private final RosterRepository rosterRepository;


    @Autowired
    public RosterService(RosterRepository rosterRepository) {
        this.rosterRepository = rosterRepository;
    }

    public Mono<Roster> save(Roster r) {
        return this.rosterRepository.save(r);
    }

    public Flux<Roster> findAll() {
        /*
        * This looks like it returns a Flux, but it is just a wrapper.
        * It is a blocking call because, we are using spring-boot-starter-data-jpa dependency with h2
        * as db connection and db, which does not support reactive operations.
        *
        * So I change the dependency
        * r2dbc-h2 and spring-boot-starter-data-r2dbc
        * */
        //this.rosterDAO.findAll()
        return this.rosterRepository.findAll();
    }
}
