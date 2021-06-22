package com.mgmt.todo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorEntity {
    private String errorMessage;
}
