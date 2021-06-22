package com.mgmt.todo.web.controller;

import com.mgmt.todo.service.TaskService;
import com.mgmt.todo.web.dto.NewOrderDTO;
import com.mgmt.todo.web.dto.Status;
import com.mgmt.todo.web.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/api/tasks/reorder")
    public List<TaskDTO> reorder(@RequestBody NewOrderDTO newOderDTO) {
        return taskService.reorderTask(newOderDTO.getTaskId(), newOderDTO.getNewPosition());
    }

    @PostMapping("/api/tasks")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.saveTask(taskDTO);
    }

    @GetMapping("/api/tasks")
    public List<TaskDTO> ListTask(@RequestParam(value = "status", required = false) Status status) {
        return taskService.listTask(status);
    }

    @DeleteMapping("/api/tasks/{id}")
    public TaskDTO createTask(@PathVariable Long id) {
        return taskService.delete(id);
    }

    @PutMapping("/api/tasks/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO,
                              @PathVariable Long id) {

        if (!id.equals(taskDTO.getId())) {
            throw new BadRequestException("Id in the path does not match with the request body");
        }
        return taskService.updateTask(taskDTO);

    }
}

