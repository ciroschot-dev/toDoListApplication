package com.proyecto.todolistapplication.exception;

public class TaskNotFoundException extends RuntimeException
{
    public TaskNotFoundException(long id)
    {
        super("The task with the id: " + id + " could not be found.");
    }
}
