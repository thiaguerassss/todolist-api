package com.thiago.todo_list.controller;

import com.thiago.todo_list.model.User;
import com.thiago.todo_list.service.UserService;
import com.thiago.todo_list.validation.CreateGroup;
import com.thiago.todo_list.validation.UpdateGroup;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Integer id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Validated(CreateGroup.class)
    public ResponseEntity<User> create(@Valid @RequestBody User user){
        user = userService.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    @Validated(UpdateGroup.class)
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable("id") Integer id){
        user.setId(id);
        user = userService.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
