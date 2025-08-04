package br.com.riachuelo.desafio_otavio.adapters.models;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.domains.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "task")
@Entity
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public static TaskModel from(Task t) {
        final TaskModel task = new TaskModel();
        task.setTitle(t.title());
        task.setDescription(t.description());
        task.setCreationDate(t.creationDate());
        task.setDueDate(t.dueDate());
        task.setStatus(t.status().name());
        return task;
    }

    public Task toTask() {
        return Task.builder()
                .id(id)
                .title(this.title)
                .description(this.description)
                .creationDate(this.creationDate)
                .dueDate(this.dueDate)
                .status(TaskStatus.valueOf(this.status))
                .build();
    }
}
