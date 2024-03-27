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

    @GetMapping("/project/{projNo}/task")
    public ResponseEntity<List<TaskDto>> getAllTasksByProjectId(@PathVariable Integer projNo) {
        List<TaskDto> tasks = taskService.getTasksByProjectId(projNo);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/project/{projNo}/task")
    public ResponseEntity<TaskDto> addTaskToProject(@PathVariable Integer projNo, @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.addTaskToProject(projNo, taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        TaskDto task = taskService.getTaskById(taskNo);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PutMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Integer projNo, @PathVariable Integer taskNo, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskNo, taskDto);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/projects/{projNo}/task/{taskNo}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer projNo, @PathVariable Integer taskNo) {
        taskService.deleteTask(taskNo);
        return ResponseEntity.ok().build();
    }
}
