package com.microrelief.task_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;

    private LocalDateTime createdAt = LocalDateTime.now();
    
    public String getAiCategory() {
		return aiCategory;
	}

	public void setAiCategory(String aiCategory) {
		this.aiCategory = aiCategory;
	}

	public Integer getUrgencyScore() {
		return urgencyScore;
	}

	public void setUrgencyScore(Integer urgencyScore) {
		this.urgencyScore = urgencyScore;
	}

	public Boolean getFlaggedUnsafe() {
		return flaggedUnsafe;
	}

	public void setFlaggedUnsafe(Boolean flaggedUnsafe) {
		this.flaggedUnsafe = flaggedUnsafe;
	}

	private String aiCategory;
    
    private Integer urgencyScore; // 1-5
    
    private Boolean flaggedUnsafe = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    // Getters and setters (generate via IDE: right-click -> Generate -> Getters and Setters)
}