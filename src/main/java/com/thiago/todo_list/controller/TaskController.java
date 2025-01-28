package com.thiago.todo_list.controller;

import com.thiago.todo_list.model.Task;
import com.thiago.todo_list.service.TaskService;
import com.thiago.todo_list.validation.CreateGroup;
import com.thiago.todo_list.validation.UpdateGroup;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable("id") Integer id){
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    @Validated(CreateGroup.class)
    public ResponseEntity<Task> create(@Valid @RequestBody Task task){
        task = taskService.create(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping("/{id}")
    @Validated(UpdateGroup.class)
    public ResponseEntity<Task> update(@Valid @RequestBody Task task, @PathVariable("id") Integer id){
        task.setId(id);
        task = taskService.update(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Task>> findByUserId(@PathVariable("user_id") Integer id){
        List<Task> tasks = taskService.findByUserId(id);
        return ResponseEntity.ok().body(tasks);
    }
}
