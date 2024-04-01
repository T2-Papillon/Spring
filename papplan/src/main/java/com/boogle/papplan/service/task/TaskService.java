// TaskService.java
package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasksByProjectId(Integer projNo);

    String addTaskToProject(Integer projNo, TaskDTO taskDto);

    TaskDTO getTaskById(Integer projNo, Integer taskNo);

    TaskDTO updateTask(Integer projNo, Integer taskNo, TaskDTO taskDto);

    void deleteTask(Integer projNo, Integer taskNo);

    // 사원이 담당하고 있는 모든 업무 조회
    List<TaskDTO> getTasksByEmpNoInProgress(Integer empNo);

}
