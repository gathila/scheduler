package com.gathi.sch.roster.dom.model;

import com.gathi.sch.roster.dao.entity.RosterUserEntity;

import java.time.LocalDateTime;

public class Roster {

    private Long id;
    private RosterUserEntity rosterUserEntity;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RosterUserEntity getRosterUser() {
        return rosterUserEntity;
    }

    public void setRosterUser(RosterUserEntity rosterUserEntity) {
        this.rosterUserEntity = rosterUserEntity;
    }


    @Override
    public String toString() {
        return "Roster{" +
                "id=" + id +
                ", rosterUserEntity=" + rosterUserEntity +
                ", fromDateTime=" + fromDateTime +
                ", toDateTime=" + toDateTime +
                '}';
    }
}
