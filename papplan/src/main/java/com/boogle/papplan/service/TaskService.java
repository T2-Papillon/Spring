    package com.boogle.papplan.service;

    import com.boogle.papplan.dto.TaskDto;
    import com.boogle.papplan.entity.Project;
    import com.boogle.papplan.entity.Task;
    import com.boogle.papplan.entity.TaskStatus;
    import com.boogle.papplan.repository.ProjectRepository;
    import com.boogle.papplan.repository.TaskRepository;
    import com.boogle.papplan.repository.TaskStatusRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class TaskService {
        private final ProjectRepository projectRepository;
        private final TaskRepository taskRepository;
        private final TaskStatusRepository taskStatusRepository;

        // Task 엔티티를 TaskDto로 변환
        private TaskDto entityToDto(Task task) {
            TaskDto dto = new TaskDto();
            dto.setTaskNo(task.getTaskNo());
            dto.setTaskTitle(task.getTaskTitle());
            dto.setTaskDesc(task.getTaskDesc());
            dto.setTaskEndDate(task.getTaskEndDate());
            if (task.getTaskStatus() != null) {
                dto.setTaskStatus(task.getTaskStatus().getTaskStatusId()); // 또는 task.getTaskStatus().getTaskStatusName()
            }
            return dto;
        }


        // TaskDto를 Task 엔티티로 변환
        private Task dtoToEntity(TaskDto dto) {
            Task task = new Task();
            task.setTaskNo(dto.getTaskNo());
            task.setTaskTitle(dto.getTaskTitle());
            task.setTaskDesc(dto.getTaskDesc());
            task.setTaskEndDate(dto.getTaskEndDate());

            // TaskStatus 처리
            if (dto.getTaskStatus() != null) {
                TaskStatus taskStatus = taskStatusRepository.findById(dto.getTaskStatus()).orElse(null);
                task.setTaskStatus(taskStatus);
            }

            return task;
        }


        // 특정 프로젝트에 속한 모든 Task를 조회하는 메서드
        public List<TaskDto> getTasksByProjectId(Integer projectId) {
            Project project = projectRepository.findById(projectId).orElse(null);
            if (project != null) {
                return taskRepository.findAllByProject(project).stream()
                        .map(this::entityToDto)
                        .collect(Collectors.toList());
            } else {
                return new ArrayList<>(); // 프로젝트가 없다면 빈 리스트 반환
            }
        }

        // 새로운 Task를 생성하는 메서드
        public TaskDto createTask(TaskDto taskDto) {
            Task task = dtoToEntity(taskDto);
            Task savedTask = taskRepository.save(task);
            return entityToDto(savedTask);
        }

        // 특정 Task를 조회하는 메서드
        public TaskDto getTaskById(Integer taskNo) {
            return taskRepository.findById(taskNo)
                    .map(this::entityToDto)
                    .orElse(null);
        }

        // Task를 수정하는 메서드
        public TaskDto updateTask(TaskDto taskDto) {
            if (taskRepository.existsById(taskDto.getTaskNo())) {
                Task task = dtoToEntity(taskDto);
                Task updatedTask = taskRepository.save(task);
                return entityToDto(updatedTask);
            } else {
                return null; // 수정할 Task가 존재하지 않으면 null 반환
            }
        }

        // Task를 삭제하는 메서드
        public void deleteTask(Integer taskNo) {
            taskRepository.deleteById(taskNo);
        }

        // 모든 Task 조회하는 메서드
        public List<TaskDto> getAllTasks() {
            return taskRepository.findAll().stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
        }

    }