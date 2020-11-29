package com.myorg.todoapp.service;

import com.myorg.todoapp.jpa.domains.TodoItem;
import com.myorg.todoapp.jpa.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TodoServiceImpl implements TodoService{

    public static final int PAGE_SIZE =     100;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoItem> getAllTodoItems(int pageNumber) {
        if(pageNumber>0) pageNumber -=1;
//        return todoRepository.findAll(PageRequest.of(pageNumber,PAGE_SIZE, Sort.Direction.DESC,"plannedDate"));
        return todoRepository.findAll(PageRequest.of(pageNumber,PAGE_SIZE, Sort.by("Status").descending().and(Sort.by("plannedDate").descending())));
    }

    @Override
    public TodoItem getTodoItemById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public TodoItem updateTodoItem(TodoItem todoItem) {
        TodoItem todoItemFromDB = todoRepository.findById(todoItem.getId()).get();
        if(todoItemFromDB != null){
            if(isNotEmpty(todoItem.getDescription())){
                todoItemFromDB.setDescription(todoItem.getDescription());
            }
            if(isNotEmpty(todoItem.getSummary())){
                todoItemFromDB.setSummary(todoItem.getSummary());
            }
            if(isNotEmpty(todoItem.getStatus().name())){
                todoItemFromDB.setStatus(todoItem.getStatus());
            }
            return this.saveTodoItem(todoItemFromDB);
        }
        return null;
    }

    @Override
    public TodoItem saveTodoItem(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    private boolean isNotEmpty(String str){
        return str != null && !str.isEmpty();
    }
}
