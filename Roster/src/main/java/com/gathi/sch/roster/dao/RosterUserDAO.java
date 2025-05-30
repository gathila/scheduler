package com.gathi.sch.roster.dao;

import com.gathi.sch.roster.dao.entity.RosterUserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RosterUserDAO extends ReactiveCrudRepository<RosterUserEntity, Long> {
}
