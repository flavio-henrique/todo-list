package com.mgmt.todo.service;

import com.mgmt.todo.persistence.model.Task;
import com.mgmt.todo.persistence.model.UserAuth;
import com.mgmt.todo.persistence.repository.TaskRepository;
import com.mgmt.todo.security.SessionProvider;
import com.mgmt.todo.web.controller.BadRequestException;
import com.mgmt.todo.web.dto.Status;
import com.mgmt.todo.web.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDTO> reorderTask(Long taskId, Long newOrder){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        Task task = taskRepository.findByIdAndUserAuthUsername(taskId, username)
                .orElseThrow(() -> new BadRequestException("Task not Found"));

        if (newOrder < task.getOrder()) {
            taskRepository.reorderOthersDown(taskId, newOrder);
        } else {
            taskRepository.reorderOthersUp(taskId, newOrder);
        }
        taskRepository.setNewPosition(taskId, newOrder);

        List<Task> tasks = taskRepository.findAllByUserAuthUsernameAndStatusOrderByOrderAsc(username,
                com.mgmt.todo.persistence.model.Status.TODO);
        return tasks.stream()
            .map(taskReordered -> TaskDTO.builder()
                    .id(taskReordered.getId())
                    .title(taskReordered.getTitle())
                    .description(taskReordered.getDescription())
                    .order(taskReordered.getOrder())
                    .status(Status.valueOf(taskReordered.getStatus().name()))
                    .build())
            .collect(Collectors.toList());
    }

    public TaskDTO updateTask(TaskDTO taskDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        taskRepository.findByIdAndUserAuthUsername(taskDTO.getId(), username)
                .orElseThrow(() -> new BadRequestException("Task not Found"));
        return saveTask(taskDTO);
    }

    public TaskDTO saveTask(TaskDTO taskDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        Task task = Task.builder()
                .id(taskDTO.getId())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .order(taskDTO.getOrder())
                .status(com.mgmt.todo.persistence.model.Status.valueOf(taskDTO.getStatus().name()))
                .userAuth(UserAuth.builder()
                        .username(username)
                        .build())
                .build();
        Task saved = taskRepository.save(task);
        return TaskDTO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .order(saved.getOrder())
                .status(Status.valueOf(saved.getStatus().name()))
                .build();
    }

    public List<TaskDTO> listTask(Status status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        List<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findAllByUserAuthUsernameAndStatusOrderByOrderAsc(username,
                    com.mgmt.todo.persistence.model.Status.valueOf(status.name()));
        } else {
            tasks = taskRepository.findAllByUserAuthUsername(username);
        }
        return tasks.stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .order(task.getOrder())
                        .status(Status.valueOf(task.getStatus().name()))
                        .build())
                .collect(Collectors.toList());
    }

    public TaskDTO delete(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Task task = taskRepository.findByIdAndUserAuthUsername(id, username)
                .orElseThrow(() -> new BadRequestException("Task not Found"));
        TaskDTO taskDTO = TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .order(task.getOrder())
                .status(Status.valueOf(task.getStatus().name()))
                .build();
        taskRepository.delete(Task.builder().id(id).build());
        return taskDTO;
    }

}
