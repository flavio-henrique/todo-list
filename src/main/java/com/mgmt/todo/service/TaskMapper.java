package com.mgmt.todo.service;

import com.mgmt.todo.persistence.model.Task;
import com.mgmt.todo.persistence.model.UserAuth;
import com.mgmt.todo.web.dto.Status;
import com.mgmt.todo.web.dto.TaskDTO;

public class TaskMapper {
    public static Task mapToTask(TaskDTO taskDTO, String username) {
        return Task.builder()
                .id(taskDTO.getId())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .order(taskDTO.getOrder())
                .status(com.mgmt.todo.persistence.model.Status.valueOf(taskDTO.getStatus().name()))
                .userAuth(UserAuth.builder()
                        .username(username)
                        .build())
                .build();
    }

    public static TaskDTO mapToTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .order(task.getOrder())
                .status(Status.valueOf(task.getStatus().name()))
                .build();
    }
}