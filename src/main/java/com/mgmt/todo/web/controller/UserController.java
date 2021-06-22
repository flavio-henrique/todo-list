package com.mgmt.todo.web.controller;

import com.mgmt.todo.service.TaskService;
import com.mgmt.todo.service.UserService;
import com.mgmt.todo.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping("/api/users")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }
}
