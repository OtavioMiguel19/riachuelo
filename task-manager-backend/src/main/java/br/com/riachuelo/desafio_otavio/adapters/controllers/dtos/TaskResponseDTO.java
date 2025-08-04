package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import br.com.riachuelo.desafio_otavio.domains.Task;
import io.swagger.v3.oas.annotations.media.Schema;

public record TaskResponseDTO(
        @Schema(description = "Unique identifier of the task", example = "123")
        long id,

        @Schema(description = "Title of the task", example = "Finish project report")
        String title,

        @Schema(description = "Detailed description of the task", example = "Complete the final report and send to the manager")
        String description,

        @Schema(description = "Task creation date in ISO format (yyyy-MM-dd)", example = "2025-07-30")
        String creationDate,

        @Schema(description = "Task due date in ISO format (yyyy-MM-dd)", example = "2025-08-10")
        String dueDate,

        @Schema(description = "Current status of the task", example = "IN_PROGRESS")
        String status
) {
    public static TaskResponseDTO fromTask(Task task) {
        return new TaskResponseDTO(
                task.id(),
                task.title(),
                task.description(),
                task.creationDate().toString(),
                task.dueDate().toString(),
                task.status().name()
        );
    }
}