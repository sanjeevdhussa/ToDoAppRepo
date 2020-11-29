package com.myorg.todoapp.jpa.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class TodoItem {

	public enum Status {
		OPEN("OPEN"), DONE("DONE");

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		private String status;

		private Status(String status){
			this.status = status;
		}

		@Override
		@JsonValue
		public String toString() {
			return status;
		}

		@JsonCreator
		public static Status fromText(String value){
			return Status.valueOf(value);
		}
	}

	public TodoItem(){

	}
	public TodoItem(String description, String summary) {
		super();
		this.description = description;
		this.summary = summary;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String description;
	@Column(nullable = false)
	private String summary;
	@Enumerated(value=EnumType.STRING)
	private Status status = Status.OPEN;
	private LocalDateTime plannedDate = LocalDateTime.now();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "TODO [id=" + id + ", details=" + description + ", status=" + status + ", dateCreated=" + plannedDate
				+ "]";
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDateTime getPlannedDate() {
		return plannedDate;
	}

	public void setPlannedDate(LocalDateTime plannedDate) {
		this.plannedDate = plannedDate;
	}
}
