package com.myorg.todoapp.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.myorg.todoapp.jpa.domains.TodoItem;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoRepositoryTest {

	@Autowired
	private TodoRepository todoRepository;

	@Before
	public void setup() {
		TodoItem todoItemItem1 = new TodoItem("first TODO description", "first TODO summary");
		todoRepository.save(todoItemItem1);
		assertThat(todoRepository.findById(todoItemItem1.getId()).get().getDescription().equals("first TODO description")).isTrue();
		TodoItem todoItemItem2 = new TodoItem("2nd TODO description", "2nd TODO summary");
		todoRepository.save(todoItemItem2);
		assertThat(todoRepository.findById(todoItemItem2.getId()).get().getDescription().equals("2nd TODO description")).isTrue();
	}

	@Test
	public void givenTodoItem_whenSavedInDB_thenRecordCreatedInDB() {
		//given
		TodoItem todoItem = new TodoItem("book a dr appt", "book appt");

		//when
		todoRepository.save(todoItem);

		//then
		TodoItem savedTodoItemItem = todoRepository.findById(todoItem.getId()).orElse(null);
		assertThat(savedTodoItemItem).isNotNull();
		assertThat(savedTodoItemItem.getStatus()).isEqualTo(TodoItem.Status.OPEN);
		assertThat(savedTodoItemItem.getDescription()).isEqualTo("book a dr appt");
	}

	@Test
	public void givemToDoItem_whenModifiedDesciption_thenDescriptionUpdatedInDB() {
		//given
		List<TodoItem> todoItemItems = todoRepository.findAll( PageRequest.of(0,10, Sort.Direction.DESC,"plannedDate"));
		assertThat(todoItemItems).isNotNull();
		//when
		TodoItem todoItem = todoItemItems.get(0);
		todoItem.setDescription("Modified TODO item");
		todoRepository.save(todoItem);
		//then
		TodoItem modifiedTodoItem = todoRepository.findById(todoItem.getId()).orElse(null);
		assertThat(modifiedTodoItem).isNotNull();
		assertThat(modifiedTodoItem.getDescription()).isEqualTo("Modified TODO item");
	}

	@Test
	public void givenToDOItem_whenStatusChanged_ThenUpdatedinDB() {
		//given
		List<TodoItem> todoItemItems = todoRepository.findAll( PageRequest.of(0,10, Sort.Direction.DESC,"plannedDate"));
		assertThat(todoItemItems).isNotNull();
		//when
		TodoItem todoItem = todoItemItems.get(0);
		todoItem.setStatus(TodoItem.Status.DONE);
		todoRepository.save(todoItem);
		//then
		TodoItem modifiedTodoItem = todoRepository.findById(todoItem.getId()).orElse(null);
		assertThat(modifiedTodoItem).isNotNull();
		assertThat(modifiedTodoItem.getStatus()).isEqualTo(TodoItem.Status.DONE);
	}

	@Test
	public void givenTodoItem_whenDeleted_thenRemovedFromDB() {
		//given
		List<TodoItem> todoItemItems = todoRepository.findAll( PageRequest.of(0,10, Sort.Direction.DESC,"plannedDate"));
		assertThat(todoItemItems).isNotNull();
		//when
		TodoItem todoItem = todoItemItems.get(0);
		todoRepository.delete(todoItem);
		//then
		TodoItem deletedTodoItemItem = todoRepository.findById(todoItem.getId()).orElse(null);
		assertThat(deletedTodoItemItem).isNull();
	}



	@Test
	public void givenListOfTODOItemsInDB_WhenGetAllFromDB_ThenReturnToDOItemList() {
		//given - List of items in DB
		//when
		List<TodoItem> todoItemItems = todoRepository.findAll(PageRequest.of(0,10, Sort.Direction.DESC,"plannedDate"));
		//then
		assertThat(todoItemItems).isNotNull().isNotEmpty();
		assertThat(todoItemItems.size()).isEqualTo(2);
	}

}
