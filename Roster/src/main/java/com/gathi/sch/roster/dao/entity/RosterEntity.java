package com.gathi.sch.roster.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("roster")
public class RosterEntity {

    @Id
    private Long id;

    @Column("roster_user_id")
    private Long rosterUserId;

    @Column("from_date_time")
    private LocalDateTime fromDateTime;

    @Column("to_date_time")
    private LocalDateTime toDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRosterUserId() {
        return rosterUserId;
    }

    public void setRosterUserId(Long rosterUserId) {
        this.rosterUserId = rosterUserId;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(LocalDateTime fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(LocalDateTime toDateTime) {
        this.toDateTime = toDateTime;
    }
}
