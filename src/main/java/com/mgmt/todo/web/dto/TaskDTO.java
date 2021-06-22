package com.mgmt.todo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long order;
    private Status status;
}
