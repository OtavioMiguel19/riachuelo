package br.com.riachuelo.desafio_otavio.domains;

import java.time.LocalDate;

public record Task(Long id, String title, String description, LocalDate creationDate, LocalDate dueDate, TaskStatus status) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDate creationDate;
        private LocalDate dueDate;
        private TaskStatus status;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder creationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Task build() {
            return new Task(id, title, description, creationDate, dueDate, status);
        }
    }
}