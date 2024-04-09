// TaskService.java
package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.entity.Task;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasksByProjectId(Integer projNo);

    String addTaskToProject(Integer projNo, TaskDTO taskDto);

    TaskDTO getTaskById(Integer projNo, Integer taskNo);

    TaskDTO updateTask(Integer projNo, Integer taskNo, TaskDTO taskDto);

    void deleteTask(Integer projNo, Integer taskNo);

    // 사원이 담당하고 있는 모든 업무 조회
    List<TaskDTO> getTasksByEmpNo(Integer empNo);

    // 모든 프로젝트의 전체 업무를 조회
    List<TaskDTO> getAllTasks();

    // 업무 검색
    List<TaskDTO> searchTasks(Integer projNo, String term, int page, int pageSize);

    List<Task> findTasksByStatusId(Integer projNo, String taskStatusId);

    List<TaskDTO> findTasksByStatusIdDto(Integer projNo, String taskStatusId);
}
