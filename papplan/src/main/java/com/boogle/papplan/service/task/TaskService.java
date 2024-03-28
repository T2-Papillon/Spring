// TaskService.java
package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasksByProjectId(Integer projNo);
    TaskDTO addTaskToProject(Integer projNo, TaskDTO taskDto);
    TaskDTO getTaskById(Integer projNo, Integer taskNo);
    TaskDTO updateTask(Integer projNo, Integer taskNo, TaskDTO taskDto);
    void deleteTask(Integer projNo, Integer taskNo);
}
