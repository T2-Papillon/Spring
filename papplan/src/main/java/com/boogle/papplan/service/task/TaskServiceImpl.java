package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDto;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.entity.TaskStatus;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskPriorityRepository;
import com.boogle.papplan.repository.TaskRepository;
import com.boogle.papplan.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private  final TaskPriorityRepository taskPriorityRepository;
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, TaskPriorityRepository taskPriorityRepository, TaskStatusRepository taskStatusRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskPriorityRepository = taskPriorityRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public List<TaskDto> getTasksByProjectId(Integer projNo) {
        List<Task> tasks = taskRepository.findAllByProjectProjNo(projNo);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto addTaskToProject(Integer projNo, TaskDto taskDto) {
        Task task = convertToEntity(taskDto);
        // projNo에 해당하는 프로젝트에 태스크 추가하는 로직을 추가하세요.
        return convertToDto(task);
    }

    @Override
    public TaskDto getTaskById(Integer projNo, Integer taskNo) {
        // projNo와 taskNo에 해당하는 태스크를 가져오는 로직을 추가하세요.
        return null;
    }

    @Override
    public TaskDto updateTask(Integer projNo, Integer taskNo, TaskDto taskDto) {
        // projNo와 taskNo에 해당하는 태스크를 업데이트하는 로직을 추가하세요.
        return null;
    }

    @Override
    public void deleteTask(Integer projNo, Integer taskNo) {
        taskRepository.deleteById(taskNo);
    }

    private TaskDto convertToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskNo(task.getTaskNo());
        taskDto.setTaskTitle(task.getTaskTitle());
        taskDto.setAssignee(task.getAssignee());
        taskDto.setTaskDesc(task.getTaskDesc());
        // 나머지 필드도 엔티티에서 가져와서 설정하세요.
        return taskDto;
    }

    private Task convertToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTaskNo(taskDto.getTaskNo());
        task.setTaskTitle(taskDto.getTaskTitle());
        task.setAssignee(taskDto.getAssignee());
        task.setTaskDesc(taskDto.getTaskDesc());
        // 나머지 필드도 DTO에서 가져와서 설정하세요.
        return task;
    }
}
