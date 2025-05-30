package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService extends UserDetailsService {

    void saveUsersToDB(MultipartFile file);

    CompletableFuture<List<User>> getAllUsers();

    UserDetails save(User user);
}
