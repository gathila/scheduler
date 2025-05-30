package com.gathi.sch.roster.dao;

import com.gathi.sch.roster.dao.entity.RosterEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterDAO extends ReactiveCrudRepository<RosterEntity, Long> {

}
