package com.boogle.papplan.service.task;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Task> findTasksByStatusId(Integer projNo, String taskStatusId) {
        if (taskStatusId == null || taskStatusId.isEmpty() || taskStatusId.equals("전체")) {
            return taskRepository.findAll();
        } else {
            return taskRepository.findByProjectProjNoAndTaskStatusTaskStatusId(projNo, taskStatusId);
        }
    }

    @Override
    public List<TaskDTO> findTasksByStatusIdDto(Integer projNo, String taskStatusId) {
        List<Task> tasks = taskRepository.findByProjectProjNoAndTaskStatusTaskStatusId(projNo, taskStatusId);
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
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

         /* // Project 번호
        Project project = task.getProject();
        project.setProjNo(taskDto.getProjNo());*/

        /*task.setTaskTitle(taskDto.getTaskTitle());  // 업무 제목
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
        task.setTaskUpdateDate((taskDto.getTaskUpdateDate()));  // 수정일*/

        if (optionalTask.isEmpty()) {
            return null;
        }
        else{
            Task task = optionalTask.get();
            String originTaskStatus = task.getTaskStatus().getTaskStatusId();
            Date originTaskFinishDate = task.getTaskFinishDate();
            System.out.println("[Update Task LOG] " + task.getTaskNo() + " >> " + " +(( " + originTaskStatus + " )) >> TO BE (( " + taskDto.getTaskStatus() + " ))");

            // 진행도가 100%가 되었거나 진행 상태가 완료로 바뀐 경우 -> 업무 종료 시간 저장
            if(!originTaskStatus.equals("DONE") && (taskDto.getTaskStatus().equals("DONE") || taskDto.getTaskPercent() >= 100)){
                taskDto.setTaskFinishDate(new Date());
            }
            // 어떤 이유에 진행도가 다시 100 미만이 되거나 완료 상태가 아니게 되었을 경우 -> 종료 시간 삭제
            else if(originTaskStatus.equals("DONE") && (!taskDto.getTaskStatus().equals("DONE") || taskDto.getTaskPercent() < 100)){
                taskDto.setTaskFinishDate(null);
            }

            try{
                taskDto.setTaskNo(taskNo);
                Task updateTask = convertToEntity(taskDto);
                taskRepository.save(updateTask);

                return convertToDto(task);
            }
            catch(Exception e){
                return null;
            }

        }


    }

    @Override
    public void deleteTask(Integer projNo, Integer taskNo) {
        taskRepository.deleteById(taskNo);
    }

    // 사원 번호로 사원이 담당하고 있는 진행중인 업무 리턴
    @Override
    public List<TaskDTO> getTasksByEmpNo(Integer empNo) {
        //List<Task> tasks = taskRepository.findAllByEmpNoInProgress(empNo);
        List<Task> tasks = taskRepository.findAllByEmpNo(empNo);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll(); // 모든 업무 조회
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> searchTasks(Integer projNo, String term, int page, int pageSize) {
        return taskRepository.findByProjectIdAndTaskTitleOrAssignee(projNo, term, PageRequest.of(page, pageSize)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TaskDTO convertToDto(Task task) {
        TaskDTO taskDto = new TaskDTO();

        taskDto.setTaskNo(task.getTaskNo());                // task 고유 번호
        taskDto.setProjNo(task.getProject().getProjNo());   // 프로젝트 번호
        taskDto.setTaskTitle(task.getTaskTitle());          // 업무 제목
        taskDto.setTaskDesc(task.getTaskDesc());            // 업무 설명
        taskDto.setTaskPriority(task.getTaskPriority().getTaskPriorityId());    // 업무 우선순위
        taskDto.setTaskStatus(task.getTaskStatus().getTaskStatusId());          // 업무 진행 상태
        taskDto.setTaskStartDate(task.getTaskStartDate());                      // 업무 시작일
        taskDto.setTaskEndDate(task.getTaskEndDate());                          // 업무 종료일
        taskDto.setTaskFinishDate(task.getTaskFinishDate());                    // 실제 업무 종료일
        taskDto.setTaskTest(task.getTaskTest());                // 업무 테스트 여부
        taskDto.setTaskTestUrl(task.getTaskTestUrl());          // 테스트 요청 URL
        taskDto.setTaskPercent(task.getTaskPercent());          // 업무 진행도
        taskDto.setTaskCreateDate(task.getTaskCreateDate());    // 업무 생성일
        taskDto.setTaskUpdateDate(task.getTaskUpdateDate());    // 업무 수정일

        // 담당자 정보가 있는 경우
        if (task.getAssignee() != null) {
            Employees assignee = task.getAssignee();
            taskDto.setAssigneeEno(assignee.getEno());
            taskDto.setAssigneeName(assignee.getName());
            // 부서 정보가 있는 경우, 부서명 설정
            if (assignee.getDepartment() != null) {
                taskDto.setAssigneeDept(assignee.getDepartment().getDept_no());
            }
        }
        return taskDto;
    }

    private Task convertToEntity(TaskDTO taskDto) {
        Task task = new Task();
        if (taskDto.getProjNo() != null) {
            Project project = projectRepository.findById(taskDto.getProjNo())
                    .orElseThrow(() -> new RuntimeException("Project not found with id: " + taskDto.getProjNo()));
            task.setProject(project);
        }

        if (taskDto.getAssigneeEno() != null) {
            employeeRepository.findById(taskDto.getAssigneeEno())
                    .ifPresentOrElse(task::setAssignee, () -> {
                        throw new RuntimeException("Employee not found with id: " + taskDto.getAssigneeEno());
                    });
        }

        task.setTaskNo(taskDto.getTaskNo());
        task.setTaskTitle(taskDto.getTaskTitle());  // 업무 제목
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
        task.setTaskEndDate(taskDto.getTaskEndDate());          // 업무 종료 예정일
        task.setTaskFinishDate(taskDto.getTaskFinishDate());    // 실제 업무 종료일
        task.setTaskPercent(taskDto.getTaskPercent());          // 업무 진행 정도(xx%)
        if(taskDto.getTaskTest() != null)
            task.setTaskTest(taskDto.getTaskTest());            // 업무 테스트 진행 여부
        task.setTaskTestUrl(taskDto.getTaskTestUrl());          // 테스트 요청 URL
        task.setTaskCreateDate(taskDto.getTaskCreateDate());    // 업무 생성일
        task.setTaskUpdateDate((taskDto.getTaskUpdateDate()));  // 수정일

        return task;
    }
}