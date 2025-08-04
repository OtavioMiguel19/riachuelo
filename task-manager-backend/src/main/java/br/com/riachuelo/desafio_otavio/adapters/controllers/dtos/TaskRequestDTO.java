package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.domains.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record TaskRequestDTO(
        @Schema(description = "Title of the task", example = "Finish project report")
        String title,

        @Schema(description = "Detailed description of the task", example = "Complete the final report and send to the manager")
        String description,

        @Schema(description = "Due date of the task in ISO format (yyyy-MM-dd)", example = "2025-08-10")
        String dueDate,

        @Schema(description = "Current status of the task. Allowed values: TODO, IN_PROGRESS, DONE", example = "TODO")
        String status
) {
    public Task toDomain() {
        return Task.builder()
                .title(title)
                .description(description)
                .dueDate(LocalDate.parse(dueDate))
                .status(TaskStatus.valueOf(status.toUpperCase()))
                .build();
    }
}