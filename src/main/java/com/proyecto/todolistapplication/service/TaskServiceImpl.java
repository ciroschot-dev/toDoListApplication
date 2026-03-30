package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.exception.TaskNotFoundException;
import com.proyecto.todolistapplication.model.Task;
import com.proyecto.todolistapplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskServiceImpl implements TaskService
{
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task)
    {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks()
    {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(long id)
    {
        Task task;

        if (taskRepository.findById(id).isPresent())
        {
            task = taskRepository.findById(id).get();
        }
        else
        {
            throw new TaskNotFoundException(id);
        }

        return task;
    }

    @Override
    public Task updateTask(Task task, long id)
    {
        Task existingTask;

        if (taskRepository.findById(id).isPresent())
        {
            existingTask = taskRepository.findById(id).get();
        }
        else
        {
            throw new TaskNotFoundException(id);
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setCompleted(task.isCompleted());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(long id)
    {
        if (taskRepository.findById(id).isPresent())
        {
            taskRepository.deleteById(id);
        }
        else
        {
            throw new TaskNotFoundException(id);
        }
    }
}
