package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.exception.TaskNotFoundException;
import com.proyecto.todolistapplication.model.Task;
import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskService
{
    private final TaskRepository taskRepository;

    public Task createTask(Task task, User user)
    {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> findAllTasksByUser(User user)
    {
        return taskRepository.findTasksByUser(user);
    }

    public Task findTaskById(long id, User user)
    {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!taskBelongToUser(task, user))
        {
            throw new AccessDeniedException("You dont have permission to view this task");
        }

        return task;
    }

    public Task updateTask(Task task, long id, User user)
    {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!taskBelongToUser(task, user))
        {
            throw new AccessDeniedException("You dont have permission to update this task");
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setCompleted(task.isCompleted());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(long id, User user)
    {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!taskBelongToUser(task, user))
        {
            throw new AccessDeniedException("You dont have permission to delete this task");
        }

        taskRepository.deleteById(id);
    }

    public boolean taskBelongToUser(Task task, User user)
    {
        return task.getUser().getId().equals(user.getId());
    }
}
