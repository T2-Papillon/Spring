package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task") // 경로를 복수형으로 변경
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // 새 Task 생성
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDto) {
        TaskDTO createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }

    // 특정 Task 조회
    @GetMapping("/{taskNo}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer taskNo) {
        TaskDTO taskDto = taskService.getTaskById(taskNo);
        if (taskDto != null) {
            return ResponseEntity.ok(taskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Task 수정
    @PutMapping("/{taskNo}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer taskNo, @RequestBody TaskDTO taskDto) {
        taskDto.setTaskNo(taskNo); // URL에서 받은 taskNo를 DTO에 설정
        TaskDTO updatedTask = taskService.updateTask(taskDto);
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
