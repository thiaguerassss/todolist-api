package com.thiago.todo_list.service;

import com.thiago.todo_list.exception.TaskDeletionFailedException;
import com.thiago.todo_list.exception.TaskNotFoundException;
import com.thiago.todo_list.model.Task;
import com.thiago.todo_list.model.User;
import com.thiago.todo_list.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Integer id){
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public Task create(Task task){
        User user = userService.findById(task.getUser().getId());
        task.setId(null);
        task = taskRepository.save(task);
        return task;
    }

    @Transactional
    public Task update(Task task){
        Task taskToUpdate = findById(task.getId());
        taskToUpdate.setDescription(task.getDescription());
        return taskRepository.save(taskToUpdate);
    }

    public void delete(Integer id){
        findById(id);
        try {
            taskRepository.deleteById(id);
        } catch (Exception e){
            throw new TaskDeletionFailedException(e);
        }
    }

    public List<Task> findByUser(User obj){
        User user = userService.findById(obj.getId());
        return user.getTasks();
    }
}
