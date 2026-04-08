package com.proyecto.todolistapplication.controller;

import com.proyecto.todolistapplication.model.MyUserDetails;
import com.proyecto.todolistapplication.model.Task;
import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return ResponseEntity.ok(taskService.createTask(task, getAuthenticatedUser()));
    }

    @RequestMapping
    public ResponseEntity<List<Task>> findAllTasks()
    {
        return ResponseEntity.ok(taskService.findAllTasksByUser(getAuthenticatedUser()));
    }

    @RequestMapping("/find-task/{id}")
    public ResponseEntity<Task> findTask(@PathVariable long id)
    {
        return ResponseEntity.ok(taskService.findTaskById(id, getAuthenticatedUser()));
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task)
    {
        return ResponseEntity.ok(taskService.updateTask(task, id, getAuthenticatedUser()));
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id)
    {
        taskService.deleteTask(id, getAuthenticatedUser());
        return ResponseEntity.noContent().build();
    }

    private User getAuthenticatedUser()
    {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (myUserDetails == null)
        {
            throw new NullPointerException("Authenticated user not found");
        }
        
        return myUserDetails.getUser();
    }
}
