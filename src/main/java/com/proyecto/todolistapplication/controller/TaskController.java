package com.proyecto.todolistapplication.controller;

import com.proyecto.todolistapplication.model.Task;
import com.proyecto.todolistapplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/todo-app")
@RestController

public class TaskController
{
    private final TaskService taskService;

    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody Task task)
    {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @RequestMapping
    public ResponseEntity<List<Task>> findAllTasks()
    {
        return ResponseEntity.ok(taskService.findAllTasks());
    }

    @RequestMapping("/find-task/{id}")
    public ResponseEntity<Task> findTask(@PathVariable long id)
    {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task)
    {
        return ResponseEntity.ok(taskService.updateTask(task, id));
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id)
    {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
