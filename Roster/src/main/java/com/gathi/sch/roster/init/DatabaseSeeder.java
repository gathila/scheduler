package com.gathi.sch.roster.init;

import com.gathi.sch.roster.dao.RosterDAO;
import com.gathi.sch.roster.dao.RosterUserDAO;
import com.gathi.sch.roster.dao.entity.RosterEntity;
import com.gathi.sch.roster.dao.entity.RosterUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private RosterDAO rosterDAO;
    private RosterUserDAO userDAO;

    @Autowired
    public DatabaseSeeder(RosterDAO rosterDAO, RosterUserDAO userDAO) {
        this.rosterDAO = rosterDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RosterUserEntity> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            RosterUserEntity user = new RosterUserEntity();
            user.setFirstName("UserFirst" + i);
            user.setLastName("UserLast" + i);
            users.add(userDAO.save(user).block());
        }

        // Create 10 roster entries
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            RosterEntity roster = new RosterEntity();
            RosterUserEntity user = users.get(i % 5); // cycle through 5 users

            LocalDateTime from = LocalDateTime.of(2024, Month.NOVEMBER, 15 + i, 10, 30, 0);
            LocalDateTime to = LocalDateTime.of(2024, Month.DECEMBER, 15 + i, 10, 30, 0);

            roster.setRosterUserId(user.getId());
            roster.setFromDateTime(from);
            roster.setToDateTime(to);

            Mono<RosterEntity> save = rosterDAO.save(roster);

//            Flux<RosterEntity> all = rosterDAO.findAll();
//            RosterEntity entity = all.blockFirst();
            System.out.println(save.block());
        }
    }
}
