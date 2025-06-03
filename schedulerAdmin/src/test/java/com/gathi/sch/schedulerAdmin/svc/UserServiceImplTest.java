package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.dao.UserDAO;
import com.gathi.sch.schedulerAdmin.domain.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserDAO userDAO;

    @Mock
    private Executor threadPoolExecutor;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        threadPoolExecutor = Executors.newSingleThreadExecutor();
        userService = new UserServiceImpl(userDAO, threadPoolExecutor);
    }

    @Test
    void testSaveUsersToDB() throws IOException {
        String csvContent = "name,email\nJohn,john@example.com";
        MockMultipartFile file = new MockMultipartFile("file", "users.csv", "text/csv", csvContent.getBytes());

        userService.saveUsersToDB(file);

        verify(userDAO, times(1)).saveAll(anyList());
    }


    @Test
    void testGetAllUsers() throws ExecutionException, InterruptedException {
        List<User> users = users();
        when(userDAO.findAll()).thenReturn(users);

        CompletableFuture<List<User>> allUsers = userService.getAllUsers();
        List<User> join = allUsers.join();
        assertEquals("ABC", join.get(0).getName());
    }

    List<User> users() {
        User u = new User();
        u.setName("ABC");
        return List.of(u);
    }
}