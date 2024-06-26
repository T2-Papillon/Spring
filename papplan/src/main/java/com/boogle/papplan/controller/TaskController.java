package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;

    // 프로젝트 ID에 해당하는 모든 태스크를 가져오는 엔드포인트
    @GetMapping("/project/{projNo}/task")
    public ResponseEntity<List<TaskDTO>> getAllTasksByProjectId(@PathVariable Integer projNo) {
        // 프로젝트 ID를 기반으로 해당 프로젝트의 모든 태스크를 가져옴
        List<TaskDTO> tasks = taskService.getTasksByProjectId(projNo);

        projectService.updateProjectProgress(projNo);
        return ResponseEntity.ok().body(tasks);
    }

    // 사용자의 업무 리스트를 가져오는 엔드포인트
    @GetMapping("/emp/{empno}")
    public ResponseEntity<List<TaskDTO>> getTasksByEmpno(@PathVariable("empno") Integer empNo) {
        List<TaskDTO> tasks;
        try{
            tasks = taskService.getTasksByEmpNo(empNo);
            if(tasks == null)
                return ResponseEntity.notFound().build();
        }
        catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tasks);
    }


    // 특정 프로젝트에 새로운 태스크를 추가하는 엔드포인트
    @PostMapping("/project/{projNo}/task")
    public ResponseEntity<String> addTaskToProject(@PathVariable Integer projNo, @RequestBody TaskDTO taskDto) {
        // 프로젝트에 새로운 태스크를 추가하고 결과를 반환

        String reqResult = taskService.addTaskToProject(projNo, taskDto);
        projectService.updateProjectProgress(projNo);
        return new ResponseEntity<>(reqResult, HttpStatus.CREATED);
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 가져오는 엔드포인트
    @GetMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        TaskDTO task = taskService.getTaskById(projNo, taskNo);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


   // 프로젝트와 태스크 ID에 해당하는 태스크를 업데이트하는 엔드포인트
    @PostMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer projNo, @PathVariable Integer taskNo, @RequestBody TaskDTO taskDto) {
        TaskDTO updatedTask = taskService.updateTask(projNo, taskNo, taskDto);
        System.out.println("check utc time : " + taskDto.getTaskStartDate() + " <><><> " + taskDto.getTaskFinishDate());
        if (updatedTask != null) {
            projectService.updateProjectProgress(projNo);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // 프로젝트와 태스크 ID에 해당하는 태스크를 삭제하는 엔드포인트
    @DeleteMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        taskService.deleteTask(projNo, taskNo);
        projectService.updateProjectProgress(projNo);

        return ResponseEntity.ok().build();
    }

    // 프로젝트 ID를 경로에서 추출하여 특정 프로젝트 내에서 업무를 검색하는 엔드포인트
    @GetMapping("/project/{projNo}/task/search")
    public ResponseEntity<List<TaskDTO>> searchTasks(@PathVariable Integer projNo,
                                                     @RequestParam String term,
                                                     @RequestParam(defaultValue = "0") String page,
                                                     @RequestParam(defaultValue = "10") String pageSize) {

        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);

        List<TaskDTO> taskDTOS = taskService.searchTasks(projNo, term,pageInt,pageSizeInt);
        return ResponseEntity.ok(taskDTOS);
    }

    // 특정 프로젝트 안의 특정 진행 상태에 해당하는 업무를 가져오는 엔드포인트
    @GetMapping("project/{projNo}/status")
    public ResponseEntity<List<TaskDTO>> findTasksByStatusId(
            @PathVariable Integer projNo,
            @RequestParam(required = false) String taskStatusId) {
        List<TaskDTO> taskDTOS = taskService.findTasksByStatusIdDto(projNo, taskStatusId);
        return ResponseEntity.ok(taskDTOS);
    }

}
