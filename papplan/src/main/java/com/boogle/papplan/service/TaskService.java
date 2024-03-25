package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;


    // 특정 프로젝트에 속한 모든 Task를 조회하는 메서드
    public List<Task> getTasksByProject(Project project) {
        return taskRepository.findAllByProject(project);
    }

    // 새로운 Task를 생성하는 메서드
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // 특정 Task를 조회하는 메서드
    public Task getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    // Task를 수정하는 메서드
    public Task updateTask(Task task) {
        // Task가 존재하는지 확인하고, 존재한다면 수정 후 저장
        if (taskRepository.existsById(task.getTaskNo())) {
            return taskRepository.save(task);
        } else {
            return null; // 수정할 Task가 존재하지 않으면 null 반환
        }
    }

    // Task를 삭제하는 메서드
    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
