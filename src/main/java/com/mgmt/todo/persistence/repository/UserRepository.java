package com.mgmt.todo.persistence.repository;

import com.mgmt.todo.persistence.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAuth, String> {
    UserAuth findByUsername(String username);
}