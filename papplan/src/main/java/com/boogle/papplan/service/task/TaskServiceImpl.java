package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }


    @Override
    public List<TaskDTO> getTasksByProjectId(Integer projNo) {
        List<Task> tasks = taskRepository.findAllByProjectProjNo(projNo);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO addTaskToProject(Integer projNo, TaskDTO taskDto) {
        Task task = convertToEntity(taskDto);
        Project project = projectRepository.findByProjNo(projNo);
        if (project == null)  return null;
        return convertToDto(task);
    }

    @Override
    public TaskDTO getTaskById(Integer projNo, Integer taskNo) {
        Task task = taskRepository.findByProjectProjNoAndTaskNo(projNo, taskNo);

        if (task == null) {
            return null;
        }
        return convertToDto(task);
    }

    @Override
    public TaskDTO updateTask(Integer projNo, Integer taskNo, TaskDTO taskDto) {
        // projNo와 taskNo에 해당하는 태스크를 업데이트하는 로직을 추가하세요.
        return null;
    }

    @Override
    public void deleteTask(Integer projNo, Integer taskNo) {
        taskRepository.deleteById(taskNo);
    }

    private TaskDTO convertToDto(Task task) {
        TaskDTO taskDto = new TaskDTO();
        taskDto.setTaskNo(task.getTaskNo());
        taskDto.setTaskTitle(task.getTaskTitle());
        taskDto.setAssignee(task.getAssignee());
        taskDto.setTaskDesc(task.getTaskDesc());
        taskDto.setTaskPriority(task.getTaskPriority());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setTaskStartDate(task.getTaskStartDate());
        taskDto.setTaskEndDate(task.getTaskEndDate());
        return taskDto;
    }

    private Task convertToEntity(TaskDTO taskDto) {
        Task task = new Task();
        task.setTaskNo(taskDto.getTaskNo());
        task.setTaskTitle(taskDto.getTaskTitle());
        task.setAssignee(taskDto.getAssignee());
        task.setTaskDesc(taskDto.getTaskDesc());
        task.setTaskPriority(taskDto.getTaskPriority());
        task.setTaskStatus(taskDto.getTaskStatus());
        task.setTaskStartDate(taskDto.getTaskStartDate());
        task.setTaskEndDate(taskDto.getTaskEndDate());
        return task;
    }
}