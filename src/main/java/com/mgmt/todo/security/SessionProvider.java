package com.mgmt.todo.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionProvider {
    private String username;
}
