package com.ajaykumar.journalApplication.controller;

import com.ajaykumar.journalApplication.apiResponse.WeatherResponse;
import com.ajaykumar.journalApplication.entity.User;
import com.ajaykumar.journalApplication.service.UserService;
import com.ajaykumar.journalApplication.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public WeatherService weatherService;


    @PutMapping
    public ResponseEntity<User> updateJournalById(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User old = userService.getEntryByUsername(username);
        old.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : old.getUsername());
        old.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : old.getPassword());
        userService.saveNewUser(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getEntryByUsername(username);
        userService.deleteEntryById(user.getId());
        return new ResponseEntity<>(user, HttpStatus.GONE);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String greeting = "";
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        if (weatherResponse != null) {
            greeting = ", today weather feels like " + weatherResponse.getCurrent().getFeelsLike();
        }
        return new ResponseEntity<>("Hi " + username + greeting, HttpStatus.OK);
    }
}
