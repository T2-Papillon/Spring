package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDto;
import com.boogle.papplan.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task") // 경로를 복수형으로 변경
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // 특정 프로젝트에 속한 모든 Task 조회
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getTasksByProjectId(@PathVariable Integer projectId) {
        List<TaskDto> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    // 새 Task 생성
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }

    // 특정 Task 조회
    @GetMapping("/{taskNo}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer taskNo) {
        TaskDto taskDto = taskService.getTaskById(taskNo);
        if (taskDto != null) {
            return ResponseEntity.ok(taskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Task 수정
    @PutMapping("/{taskNo}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Integer taskNo, @RequestBody TaskDto taskDto) {
        taskDto.setTaskNo(taskNo); // URL에서 받은 taskNo를 DTO에 설정
        TaskDto updatedTask = taskService.updateTask(taskDto);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Task 삭제
    @DeleteMapping("/{taskNo}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskNo) {
        taskService.deleteTask(taskNo);
        return ResponseEntity.ok().build();
    }
}
