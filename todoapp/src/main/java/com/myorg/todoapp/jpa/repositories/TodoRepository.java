package com.myorg.todoapp.jpa.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.myorg.todoapp.jpa.domains.TodoItem;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoItem, Long> {
	List<TodoItem> findAll(Pageable pageable);
}
