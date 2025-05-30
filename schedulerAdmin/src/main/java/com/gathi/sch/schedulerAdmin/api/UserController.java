package com.gathi.sch.schedulerAdmin.api;

import com.gathi.sch.schedulerAdmin.domain.User;
import com.gathi.sch.schedulerAdmin.svc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/userFile",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity<Object> saveAll(@RequestParam(value = "files") MultipartFile [] files) {

        for (MultipartFile file: files) {
            this.userService.saveUsersToDB(file);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        CompletableFuture<ResponseEntity<List<User>>> responseEntityCompletableFuture = this.userService.getAllUsers()
                .thenApply(ResponseEntity::ok);

        System.out.println("executed");

        return responseEntityCompletableFuture;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(new User());
    }

    @PostMapping
    public User save(@RequestBody User user) {
        //
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return user;
    }
}
