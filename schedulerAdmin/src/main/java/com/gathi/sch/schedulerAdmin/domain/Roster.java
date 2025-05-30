package com.gathi.sch.schedulerAdmin.domain;


import java.time.LocalDateTime;

public class Roster {

    private Long id;

    private RosterUser rosterUser;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RosterUser getRosterUser() {
        return rosterUser;
    }

    public void setRosterUser(RosterUser rosterUser) {
        this.rosterUser = rosterUser;
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
