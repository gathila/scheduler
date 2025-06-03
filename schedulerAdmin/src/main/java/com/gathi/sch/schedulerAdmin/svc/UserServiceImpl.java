package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.dao.UserDAO;
import com.gathi.sch.schedulerAdmin.domain.User;
import com.gathi.sch.schedulerAdmin.domain.exception.UserAlreadyExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@Service
public class UserServiceImpl implements UserService {


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    private final Executor threadPoolExecutor;


    @Autowired
    public UserServiceImpl(UserDAO userDAO, Executor threadPoolExecutor) {
        this.userDAO = userDAO;
        this.threadPoolExecutor = threadPoolExecutor;
    }


    @Async
    public void saveUsersToDB(MultipartFile file) {

        long startTime = System.currentTimeMillis();
        List<User> records = readCSV(file);

        userDAO.saveAll(records);

        long endTime = System.currentTimeMillis();

        logger.info("Total time {}", (endTime - startTime));

    }


    public CompletableFuture<List<User>> getAllUsers() {


        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.userDAO.findAll();
            } catch (Exception e) {
                throw new CompletionException(new RuntimeException("User fetch failed", e));
            }
        }, threadPoolExecutor);
    }

//    List<User> findAll(int count) {
//        if (count < 0) {
//            throw new RuntimeException("Count is less than 0");
//        }
//
//        return List.of();
//    }


    @Async("threadPoolExecutor")
    public CompletableFuture<List<User>> getAllUsers1() {
        List<User> all = this.userDAO.findAll();
        return CompletableFuture.completedFuture(all);
    }

    private List<User> readCSV(MultipartFile file) {
        List<User> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                User u = new User();
                u.setName(values[0]);
                u.setEmail(values[1]);
                records.add(u);
            }

            return records;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User save(User user) {
        try {
            return this.userDAO.save(user);
        } catch (RuntimeException e) {
            throw new UserAlreadyExist();
        }
    }
}
