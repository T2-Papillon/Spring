package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Task;
import com.boogle.papplan.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // 특정 프로젝트에 속한 모든 Task 조회
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable Integer projectId) {
        // 프로젝트 ID를 사용하여 Task 조회 로직 구현 필요
        // 예시에서는 단순히 Service의 메서드를 호출하는 것으로 표현
        // 실제로는 projectId를 이용하여 Project 객체를 조회한 후 해당 객체를 사용하여 Task 조회
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    // 새 Task 생성
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    // 특정 Task 조회
    @GetMapping("/{task_no}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer task_no) {
        Task task = taskService.getTaskById(task_no);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Task 수정
    @PostMapping("/{task_no}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer task_no, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Task 삭제
    @DeleteMapping("/{task_no}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer task_no) {
        taskService.deleteTask(task_no);
        return ResponseEntity.ok().build();
    }
}

