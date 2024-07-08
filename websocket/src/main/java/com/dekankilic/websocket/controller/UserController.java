package com.dekankilic.websocket.controller;

import com.dekankilic.websocket.model.User;
import com.dekankilic.websocket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // This will be a message mapping, so we want after user is connected, we need to notify all the users and send a message to our web socket.
    @MessageMapping("/user.addUser")
    // To forward and inform all the connected users that we have a new user joining, we will display this new user in the list of the connected users
    @SendTo("/user/topic") // This is the new queue that will be automatically created, we will be sending all the notifications to this queue.
    public User addUser(@Payload User user){
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic") // I will notify the same queue that some user is disconnected.
    public User disconnect(@Payload User user){
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findConnectedUsers());
    }
}
