package com.gathi.sch.roster.dom.repo;

import com.gathi.sch.roster.dom.model.Roster;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RosterRepository {

    Mono<Roster> findById(Long id);

    Flux<Roster> findAll();

    Mono<Roster> save(Roster roster);
}
