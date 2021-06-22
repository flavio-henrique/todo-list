package com.mgmt.todo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewOrderDTO {
    private Long taskId;
    private Long newPosition;
}
