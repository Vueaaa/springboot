package org.example.aispringboot.repository;

import org.example.aispringboot.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findByUserId(Long userId);

    Optional<TodoEntity> findByIdAndUserId(Long id, Long userId);
}
