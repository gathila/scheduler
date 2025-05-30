package com.gathi.sch.roster.dao;

import com.gathi.sch.roster.dao.entity.RosterEntity;
import com.gathi.sch.roster.dao.entity.RosterUserEntity;
import com.gathi.sch.roster.dom.model.Roster;
import com.gathi.sch.roster.dom.repo.RosterRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RosterRepositoryImpl implements RosterRepository {

    private final RosterDAO rosterDAO;
    private final RosterUserDAO rosterUserDAO;

    public RosterRepositoryImpl(RosterDAO rosterDAO, RosterUserDAO rosterUserDAO) {
        this.rosterDAO = rosterDAO;
        this.rosterUserDAO = rosterUserDAO;
    }

    public Mono<Roster> findById(Long id) {
        return this.rosterDAO.findById(id)
                .flatMap(rosterEntity ->
                    this.rosterUserDAO.findById(rosterEntity.getRosterUserId())
                            .map(rosterUserEntity -> createRoster(rosterEntity, rosterUserEntity))
                );
    }


    public Flux<Roster> findAll() {
        return this.rosterDAO.findAll()
                .flatMap(rosterEntity ->
                        this.rosterUserDAO.findById(rosterEntity.getRosterUserId())
                                .map(rosterUserEntity -> createRoster(rosterEntity, rosterUserEntity))
                );
    }

    @Override
    public Mono<Roster> save(Roster roster) {
        RosterEntity entity = new RosterEntity();
        entity.setId(roster.getId());
        entity.setFromDateTime(roster.getFromDateTime());
        entity.setToDateTime(roster.getToDateTime());
        entity.setRosterUserId(roster.getRosterUser().getId());
        return this.rosterDAO.save(entity)
                .map(rosterEntity -> createRoster(rosterEntity, roster.getRosterUser()));
    }

    private Roster createRoster(RosterEntity rosterEntity, RosterUserEntity rosterUserEntity) {
        Roster r = new Roster();
        r.setId(rosterEntity.getId());
        r.setFromDateTime(rosterEntity.getFromDateTime());
        r.setToDateTime(rosterEntity.getToDateTime());
        r.setRosterUser(rosterUserEntity);
        return r;
    }
}
