package com.proyecto.todolistapplication.controller;

import com.proyecto.todolistapplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/todo-app")
@RestController

public class TaskController
{
    private final TaskService taskService;



}
