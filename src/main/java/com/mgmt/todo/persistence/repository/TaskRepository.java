package com.mgmt.todo.persistence.repository;

import com.mgmt.todo.persistence.model.Status;
import com.mgmt.todo.persistence.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findById(Long id);
    List<Task> findAllByUserAuthUsername(String username);
    List<Task> findAllByUserAuthUsernameAndStatusOrderByOrderAsc(String username, Status status);
    Optional<Task> findByIdAndUserAuthUsername(Long id, String username);

    @Query(value = "update task Set order_task=order_task+1 where order_task >= :newOrder \n" +
                    " and order_task <(Select order_task from task where id = :id)", nativeQuery = true)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    void reorderOthersDown(@Param("id") Long taskId, @Param("newOrder") Long newOrder);

    @Query(value = "update task Set order_task = :newOrder Where id = :id", nativeQuery = true)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    void setNewPosition(@Param("id") Long taskId, @Param("newOrder") Long newOrder);

    @Query(value = "update task Set order_task=order_task-1 where order_task <= :newOrder \n" +
            " and order_task >(Select order_task from task where id = :id)", nativeQuery = true)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    void reorderOthersUp(@Param("id") Long taskId, @Param("newOrder") Long newOrder);


}