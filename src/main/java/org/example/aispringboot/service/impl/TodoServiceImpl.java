package org.example.aispringboot.service.impl;

import org.example.aispringboot.DTO.command.TodoCreateCommandDTO;
import org.example.aispringboot.DTO.command.TodoUpdateCommandDTO;
import org.example.aispringboot.DTO.response.TodoResponseDTO;
import org.example.aispringboot.common.BusinessException;
import org.example.aispringboot.common.ResultCode;
import org.example.aispringboot.entity.TodoEntity;
import org.example.aispringboot.repository.TodoRepository;
import org.example.aispringboot.service.TodoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDTO createTodo(
            Long userId,
            TodoCreateCommandDTO commandDTO) {

        TodoEntity todo = new TodoEntity();
        todo.setUserId(userId);
        todo.setTitle(commandDTO.getTitle());
        todo.setCompleted(false);

        LocalDateTime now = LocalDateTime.now();
        todo.setCreateTime(now);
        todo.setUpdateTime(now);

        return toResponseDTO(todoRepository.save(todo));
    }

    @Override
    public List<TodoResponseDTO> listTodos(Long userId) {
        return todoRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public TodoResponseDTO getTodo(Long userId, Long todoId) {
        return toResponseDTO(findOwnedTodo(userId, todoId));
    }

    @Override
    public TodoResponseDTO updateTodo(
            Long userId,
            Long todoId,
            TodoUpdateCommandDTO commandDTO) {

        TodoEntity todo = findOwnedTodo(userId, todoId);

        if (commandDTO.getTitle() != null
                && !commandDTO.getTitle().isBlank()) {
            todo.setTitle(commandDTO.getTitle());
        }
        if (commandDTO.getCompleted() != null) {
            todo.setCompleted(commandDTO.getCompleted());
        }

        todo.setUpdateTime(LocalDateTime.now());
        return toResponseDTO(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long userId, Long todoId) {
        todoRepository.delete(findOwnedTodo(userId, todoId));
    }

    private TodoEntity findOwnedTodo(Long userId, Long todoId) {
        return todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new BusinessException(
                        ResultCode.TODO_NOT_FOUND));
    }

    private TodoResponseDTO toResponseDTO(TodoEntity todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getCompleted(),
                todo.getCreateTime(),
                todo.getUpdateTime());
    }
}
