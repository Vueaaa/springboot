package org.example.aispringboot.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.aispringboot.DTO.command.TodoCreateCommandDTO;
import org.example.aispringboot.DTO.command.TodoUpdateCommandDTO;
import org.example.aispringboot.DTO.response.TodoResponseDTO;
import org.example.aispringboot.common.Result;
import org.example.aispringboot.service.TodoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public Result<TodoResponseDTO> createTodo(
            HttpServletRequest request,
            @Valid @RequestBody TodoCreateCommandDTO commandDTO) {
        return Result.ok(todoService.createTodo(currentUserId(request), commandDTO));
    }

    @GetMapping
    public Result<List<TodoResponseDTO>> listTodos(HttpServletRequest request) {
        return Result.ok(todoService.listTodos(currentUserId(request)));
    }

    @GetMapping("/{id}")
    public Result<TodoResponseDTO> getTodo(
            HttpServletRequest request,
            @PathVariable Long id) {
        return Result.ok(todoService.getTodo(currentUserId(request), id));
    }

    @PutMapping("/{id}")
    public Result<TodoResponseDTO> updateTodo(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody TodoUpdateCommandDTO commandDTO) {
        return Result.ok(todoService.updateTodo(
                currentUserId(request), id, commandDTO));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteTodo(
            HttpServletRequest request,
            @PathVariable Long id) {
        todoService.deleteTodo(currentUserId(request), id);
        return Result.ok("删除成功");
    }

    private Long currentUserId(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return claims.get("userId", Long.class);
    }
}
