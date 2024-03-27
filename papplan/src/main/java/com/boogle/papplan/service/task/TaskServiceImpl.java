package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Override
    public List<TaskDto> getTasksByProjectId(Integer projNo) {
        return null;
    }

    @Override
    public TaskDto addTaskToProject(Integer projNo, TaskDto taskDto) {
        return null;
    }

    @Override
    public TaskDto getTaskById(Integer taskNo) {
        return null;
    }

    @Override
    public TaskDto updateTask(Integer taskNo, TaskDto taskDto) {
        return null;
    }

    @Override
    public void deleteTask(Integer taskNo) {

    }
}
