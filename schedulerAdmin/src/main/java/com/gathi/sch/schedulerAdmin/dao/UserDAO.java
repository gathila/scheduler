package com.gathi.sch.schedulerAdmin.dao;

import com.gathi.sch.schedulerAdmin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
