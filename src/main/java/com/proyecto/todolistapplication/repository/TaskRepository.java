package com.proyecto.todolistapplication.repository;

import com.proyecto.todolistapplication.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public class TaskRepository extends JpaRepository<Task, Long>
{
}
