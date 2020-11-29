package com.myorg.todoapp.rest;

import com.myorg.todoapp.jpa.domains.TodoItem;
import com.myorg.todoapp.service.TodoService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/todo", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public List<TodoItem> getAllTodoItems(@RequestParam(name="pageNumber", required = false, defaultValue="1") int pageNumber) {
        try {
            return todoService.getAllTodoItems(pageNumber);
        }catch(Exception ex){

            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in getting TODO items", ex);
            }
    }

    @GetMapping(value="/{id}")
    public TodoItem getTodoItemById(@PathVariable Long id) {
        try {
            return todoService.getTodoItemById(id);
        }catch(Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error in finding TODO item", ex);
        }
    }

    @PutMapping(value="/{id}")
    public TodoItem updateTodoItem(@RequestBody TodoItem todoItem, @PathVariable Long id) {
        todoItem.setId(id);
        try{
            return todoService.updateTodoItem(todoItem);
        }catch(Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error in updating TODO item", ex);
        }
    }

    @PostMapping
    public TodoItem saveTodoItem(@RequestBody TodoItem todoItem) {
        try {
            return todoService.saveTodoItem(todoItem);
        }catch(Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error in saving TODO item", ex);
        }
    }

    @DeleteMapping(value="/{id}", consumes = MediaType.ALL_VALUE)
    public void deleteTodoItem(@PathVariable Long id) {
        try {
            todoService.deleteById(id);
        }catch(Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error in deleting TODO item", ex);
        }
    }
}
