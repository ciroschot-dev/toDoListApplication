package com.proyecto.todolistapplication.repository;

import com.proyecto.todolistapplication.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long>
{
}
