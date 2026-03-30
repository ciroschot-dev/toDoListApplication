package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.model.Task;

import java.util.List;

public interface TaskService
{
    Task createTask(Task task);

    List<Task> findAllTasks();

    Task findTaskById(long id);

    Task updateTask(Task task, long id);

    void deleteTask(long id);
}
