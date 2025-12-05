package com.ajaykumar.journalApplication.controller;

import com.ajaykumar.journalApplication.entity.User;
import com.ajaykumar.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userService.getEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createNewAdmin")
    public ResponseEntity<?> createNewAdmin(@RequestBody User user) {
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
