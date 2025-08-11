package com.example.Simple.Task.Management.System.service;
import com.example.Simple.Task.Management.System.mapper.TaskMapper;
import com.example.Simple.Task.Management.System.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllTasks() {
        return taskMapper.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskMapper.findById(id);
    }

    public Task createTask(Task task) {
        taskMapper.insert(task);
        return task;
    }

    public boolean updateTask(Long id, Task task) {
        task.setId(id);
        return taskMapper.update(task) > 0;
    }

    public boolean deleteTask(Long id) {
        return taskMapper.deleteById(id) > 0;
    }
}