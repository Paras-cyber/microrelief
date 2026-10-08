package com.microrelief.ai_service.dto;

public class TaskCreatedEvent {
    private Long taskId;
    private String title;
    private String description;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    // Getters and setters, plus a no-args constructor
}
