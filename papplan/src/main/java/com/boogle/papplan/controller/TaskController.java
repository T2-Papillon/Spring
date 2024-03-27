// TaskController.java
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

    // 프로젝트 ID에 해당하는 모든 태스크를 가져오기
    @GetMapping("/project/{projNo}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasksByProjectId(@PathVariable Integer projNo) {
        List<TaskDto> tasks = taskService.getTasksByProjectId(projNo);
        return ResponseEntity.ok(tasks);
    }

    // 프로젝트에 새로운 태스크를 추가
    @PostMapping("/project/{projNo}/task")
    public ResponseEntity<TaskDto> addTaskToProject(@PathVariable Integer projNo, @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.addTaskToProject(projNo, taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 가져오는
    @GetMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> getTaskByIdentifier(@PathVariable Integer projNo, @PathVariable Integer taskNo) { // 수정된 부분
        TaskDto task = taskService.getTaskById(taskNo);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 업데이트
    @PutMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Integer projNo, @PathVariable Integer taskNo, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskNo, taskDto);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }

    // 프로젝트와 태스크 ID에 해당하는 태스크를 삭제
    @DeleteMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        taskService.deleteTask(taskNo);
        return ResponseEntity.ok().build();
    }
}
