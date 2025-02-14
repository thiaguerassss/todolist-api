package com.thiago.todo_list.repository;

import com.thiago.todo_list.model.Task;
import com.thiago.todo_list.model.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<TaskProjection> findByUser_Id(Integer id);
}
