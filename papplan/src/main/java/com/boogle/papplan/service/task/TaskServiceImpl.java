package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.entity.TaskPriority;
import com.boogle.papplan.entity.TaskStatus;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
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
    public String addTaskToProject(Integer projNo, TaskDTO taskDto) {
        Task task;
        Project project = projectRepository.findByProjNo(projNo);
        if (project == null)
            return "프로젝트가 존재하지 않습니다.";
        try{
            taskDto.setProjNo(projNo);
            task = convertToEntity(taskDto);
            taskRepository.save(task);
            return "success";
        }
        catch(Exception e){
            return e.getMessage();
        }
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
        // 프로젝트 ID와 태스크 ID를 사용하여 해당하는 태스크를 조회합니다.
        Optional<Task> optionalTask = Optional.ofNullable(taskRepository.findByProjectProjNoAndTaskNo(projNo, taskNo));

        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();

        // Project 번호
        Project project = task.getProject();
        project.setProjNo(taskDto.getProjNo());

        task.setTaskTitle(taskDto.getTaskTitle());  // 업무 제목
        task.setAssignee(taskDto.getAssignee());    // 업무 담당자 이름
        task.setTaskDesc(taskDto.getTaskDesc());    // 업무 설명

        // 업무 우선순위
        TaskPriority taskPriority = new TaskPriority();
        taskPriority.setTaskPriorityId(taskDto.getTaskPriority());
        task.setTaskPriority(taskPriority);

        // 업무 진행상태
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTaskStatusId(taskDto.getTaskStatus());
        task.setTaskStatus(taskStatus);

        task.setTaskStartDate(taskDto.getTaskStartDate());      // 업무 시작일
        task.setTaskEndDate(taskDto.getTaskEndDate());          // 업무 종료일
        task.setTaskPercent(taskDto.getTaskPercent());          // 업무 진행 정도(xx%)
        if(taskDto.getTaskTest() != null)
            task.setTaskTest(taskDto.getTaskTest());                // 업무 테스트 진행 여부
        task.setTaskCreateDate(taskDto.getTaskCreateDate());    // 업무 생성일
        task.setTaskUpdateDate((taskDto.getTaskUpdateDate()));  // 수정일

        task = taskRepository.save(task);

        return convertToDto(task);
    }



    @Override
    public void deleteTask(Integer projNo, Integer taskNo) {
        taskRepository.deleteById(taskNo);
    }

    // 사원 번호로 사원이 담당하고 있는 진행중인 업무 리턴
    @Override
    public List<TaskDTO> getTasksByEmpNoInProgress(Integer empNo) {
        List<Task> tasks = taskRepository.findAllByEmpNoInProgress(empNo);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TaskDTO convertToDto(Task task) {
        TaskDTO taskDto = new TaskDTO();

        taskDto.setTaskNo(task.getTaskNo());                // task 고유 번호
        taskDto.setProjNo(task.getProject().getProjNo());   // 프로젝트 번호
        taskDto.setTaskTitle(task.getTaskTitle());          // 업무 제목
        taskDto.setAssignee(task.getAssignee());            // 업무 담당자
        taskDto.setTaskDesc(task.getTaskDesc());            // 업무 설명
        taskDto.setTaskPriority(task.getTaskPriority().getTaskPriorityId());    // 업무 우선순위
        taskDto.setTaskStatus(task.getTaskStatus().getTaskStatusId());          // 업무 진행 상태
        taskDto.setTaskStartDate(task.getTaskStartDate());                      // 업무 시작일
        taskDto.setTaskEndDate(task.getTaskEndDate());                          // 업무 종료일
        taskDto.setTaskTest(task.getTaskTest());                // 업무 테스트 여부
        taskDto.setTaskPercent(task.getTaskPercent());          // 업무 진행도
        taskDto.setTaskCreateDate(task.getTaskCreateDate());    // 업무 생성일
        taskDto.setTaskUpdateDate(task.getTaskUpdateDate());    // 업무 수정일
        return taskDto;
    }

    private Task convertToEntity(TaskDTO taskDto) {
        Task task = new Task();

        // Project 번호
        Project project = new Project();
        project.setProjNo(taskDto.getProjNo());
        task.setProject(project);

        task.setTaskTitle(taskDto.getTaskTitle());  // 업무 제목
        task.setAssignee(taskDto.getAssignee());    // 업무 담당자 이름
        task.setTaskDesc(taskDto.getTaskDesc());    // 업무 설명

        // 업무 우선순위
        TaskPriority taskPriority = new TaskPriority();
        taskPriority.setTaskPriorityId(taskDto.getTaskPriority());
        task.setTaskPriority(taskPriority);

        // 업무 진행상태
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTaskStatusId(taskDto.getTaskStatus());
        task.setTaskStatus(taskStatus);

        task.setTaskStartDate(taskDto.getTaskStartDate());      // 업무 시작일
        task.setTaskEndDate(taskDto.getTaskEndDate());          // 업무 종료일
        task.setTaskPercent(taskDto.getTaskPercent());          // 업무 진행 정도(xx%)
        if(taskDto.getTaskTest() != null)
            task.setTaskTest(taskDto.getTaskTest());                // 업무 테스트 진행 여부
        task.setTaskCreateDate(taskDto.getTaskCreateDate());    // 업무 생성일
        task.setTaskUpdateDate((taskDto.getTaskUpdateDate()));  // 수정일

        return task;
    }
}