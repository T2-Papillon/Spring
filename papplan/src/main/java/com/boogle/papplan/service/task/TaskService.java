// TaskService.java
package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDto;
import java.util.List;

public interface TaskService {
    List<TaskDto> getTasksByProjectId(Integer projNo);
    TaskDto addTaskToProject(Integer projNo, TaskDto taskDto);
    TaskDto getTaskById(Integer taskNo);
    TaskDto updateTask(Integer taskNo, TaskDto taskDto);
    void deleteTask(Integer taskNo);
}


