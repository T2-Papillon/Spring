package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDto;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    // 프로젝트 ID에 해당하는 모든 태스크를 가져오는 엔드포인트
    @Override
    public List<TaskDto> getTasksByProjectId(Integer projNo) {
        List<Task> tasks = taskRepository.findAllByProjectProjNo(projNo);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 프로젝트에 새로운 태스크를 추가하는 엔드포인트
    @Override
    public TaskDto addTaskToProject(Integer projNo, TaskDto taskDto) {
        Task task = convertToEntity(taskDto);
        Project project = projectRepository.findByProjNo(projNo);
        if (project == null)  return null;
        return convertToDto(task);
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 가져오는 엔드포인트
    @Override
    public TaskDto getTaskById(Integer projNo, Integer taskNo) {
        Task task = taskRepository.findByProjectProjNoAndTaskNo(projNo, taskNo);

        if (task == null) {
            return null;
        }
        return convertToDto(task);
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 업데이트하는 엔드포인트
    @Override
    public TaskDto updateTask(Integer projNo, Integer taskNo, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findByTaskNoAndProjectProjNo(taskNo, projNo);

        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();
        task.setTaskTitle(taskDto.getTaskTitle());
        task.setAssignee(taskDto.getAssignee());
        task.setTaskDesc(taskDto.getTaskDesc());
        task.setTaskPriority(taskDto.getTaskPriority());
        task.setTaskStatus(taskDto.getTaskStatus());
        task.setTaskStartDate(taskDto.getTaskStartDate());
        task.setTaskEndDate(taskDto.getTaskEndDate());

        task = taskRepository.save(task);

        return convertToDto(task);
    }


    // 프로젝트와 태스크 ID에 해당하는 태스크를 삭제하는 엔드포인트
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
        taskDto.setTaskPriority(task.getTaskPriority());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setTaskStartDate(task.getTaskStartDate());
        taskDto.setTaskEndDate(task.getTaskEndDate());
        return taskDto;
    }

    private Task convertToEntity(TaskDto taskDto) {
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
