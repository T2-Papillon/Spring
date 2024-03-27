package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDto;
import com.boogle.papplan.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // 프로젝트 ID에 해당하는 모든 태스크를 가져오는 엔드포인트
    @GetMapping("/project/{projNo}/task")
    public ResponseEntity<List<TaskDto>> getAllTasksByProjectId(@PathVariable Integer projNo) {
        // 프로젝트 ID를 기반으로 해당 프로젝트의 모든 태스크를 가져옴
        List<TaskDto> tasks = taskService.getTasksByProjectId(projNo);
        return ResponseEntity.ok().body(tasks);
    }

    // 프로젝트에 새로운 태스크를 추가하는 엔드포인트
    @PostMapping("/project/{projNo}/task")
    public ResponseEntity<TaskDto> addTaskToProject(@PathVariable Integer projNo, @RequestBody TaskDto taskDto) {
        // 프로젝트에 새로운 태스크를 추가하고 결과를 반환
        TaskDto createdTask = taskService.addTaskToProject(projNo, taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 가져오는 엔드포인트
    @GetMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        // 프로젝트와 태스크 ID에 해당하는 태스크를 가져옴
        TaskDto task = taskService.getTaskById(projNo, taskNo);
        // 가져온 태스크가 null이 아닌 경우 - 태스크 반환
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            // 가져온 태스크가 null인 경우 - 해당 태스크를 찾을 수 없음을 응답
            return ResponseEntity.notFound().build();
        }
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 업데이트하는 엔드포인트
    @PutMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Integer projNo, @PathVariable Integer taskNo, @RequestBody TaskDto taskDto) {
        // 프로젝트와 태스크 ID에 해당하는 태스크를 업데이트하고 결과를 반환
        TaskDto updatedTask = taskService.updateTask(projNo, taskNo, taskDto);

        // 업데이트된 태스크가 null이 아닌 경우 - 업데이트 성공
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            // 업데이트된 태스크가 null인 경우 - 해당 태스크를 찾을 수 없음
            return ResponseEntity.notFound().build();
        }
    }


    // 프로젝트와 태스크 ID에 해당하는 태스크를 삭제하는 엔드포인트
    @DeleteMapping("/project/{projNo}/task/{taskNo}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        // 프로젝트와 태스크 ID에 해당하는 태스크를 삭제
        taskService.deleteTask(taskNo);
        return ResponseEntity.ok().build();
    }

}
