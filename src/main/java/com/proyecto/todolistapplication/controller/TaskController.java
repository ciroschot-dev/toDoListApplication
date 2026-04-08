package com.proyecto.todolistapplication.controller;

import com.proyecto.todolistapplication.model.MyUserDetails;
import com.proyecto.todolistapplication.model.Task;
import com.proyecto.todolistapplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/todo-app")
@RestController

public class TaskController
{
    private final TaskService taskService;

    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        return ResponseEntity.ok(taskService.createTask(task, myUserDetails.getUser()));
    }

    @RequestMapping
    public ResponseEntity<List<Task>> findAllTasks(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        return ResponseEntity.ok(taskService.findAllTasksByUser(myUserDetails.getUser()));
    }

    @RequestMapping("/find-task/{id}")
    public ResponseEntity<Task> findTask(@PathVariable long id, @AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        return ResponseEntity.ok(taskService.findTaskById(id, myUserDetails.getUser()));
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task, @AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        return ResponseEntity.ok(taskService.updateTask(task, id, myUserDetails.getUser()));
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id, @AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        taskService.deleteTask(id, myUserDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}
