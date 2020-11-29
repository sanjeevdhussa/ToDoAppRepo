package com.myorg.todoapp.service;

import com.myorg.todoapp.jpa.domains.TodoItem;

import java.util.List;

public interface TodoService {
    List<TodoItem> getAllTodoItems(int pageNumber);
    TodoItem getTodoItemById(Long id);
    TodoItem updateTodoItem(TodoItem todoItem);
    TodoItem saveTodoItem(TodoItem todoItem);
    void deleteById(Long id);
}
