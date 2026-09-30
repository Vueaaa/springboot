package org.example.aispringboot.service;

import org.example.aispringboot.DTO.command.TodoCreateCommandDTO;
import org.example.aispringboot.DTO.command.TodoUpdateCommandDTO;
import org.example.aispringboot.DTO.response.TodoResponseDTO;

import java.util.List;

public interface TodoService {

    TodoResponseDTO createTodo(Long userId, TodoCreateCommandDTO commandDTO);

    List<TodoResponseDTO> listTodos(Long userId);

    TodoResponseDTO getTodo(Long userId, Long todoId);

    TodoResponseDTO updateTodo(
            Long userId,
            Long todoId,
            TodoUpdateCommandDTO commandDTO);

    void deleteTodo(Long userId, Long todoId);
}
