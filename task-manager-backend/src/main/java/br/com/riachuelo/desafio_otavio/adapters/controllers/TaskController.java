package br.com.riachuelo.desafio_otavio.adapters.controllers;

import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.TaskRequestDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.TaskResponseDTO;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.CreateTaskUseCase;
import br.com.riachuelo.desafio_otavio.usecases.DeleteTaskUseCase;
import br.com.riachuelo.desafio_otavio.usecases.RetrieveTasksUseCase;
import br.com.riachuelo.desafio_otavio.usecases.UpdateTaskUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("tasks")
@Tag(name = "Tasks", description = "Tasks operations.")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final CreateTaskUseCase createTaskUseCase;
    private final RetrieveTasksUseCase retrieveTasksUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase, RetrieveTasksUseCase retrieveTasksUseCase, UpdateTaskUseCase updateTaskUseCase, DeleteTaskUseCase deleteTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.retrieveTasksUseCase = retrieveTasksUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Create a new task for current authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
    })
    ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO task) {
        final Task taskResult = createTaskUseCase.create(task.toDomain());
        return ResponseEntity.ok(TaskResponseDTO.fromTask(taskResult));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets a single task", description = "Returns a single task referred by it's id if found in user's tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    ResponseEntity<TaskResponseDTO> getTask(@PathVariable long id) {
        final Task result = retrieveTasksUseCase.retrieveSingle(id);
        return ResponseEntity.ok(TaskResponseDTO.fromTask(result));
    }

    @GetMapping
    @Operation(summary = "Gets all current user's tasks", description = "Returns all the tasks associated with current authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
    })
    ResponseEntity<Collection<TaskResponseDTO>> getAllTasks() {
        final List<Task> result = retrieveTasksUseCase.retrieveAllForCurrentUser();
        return ResponseEntity.ok(result.stream().map(TaskResponseDTO::fromTask).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a task", description = "Updates the task referred by it's id if found in user's tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    ResponseEntity<TaskResponseDTO> updateTask(@PathVariable long id, @RequestBody TaskRequestDTO task) {
        final Task result = updateTaskUseCase.update(id, task.toDomain());
        return ResponseEntity.ok(TaskResponseDTO.fromTask(result));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a task", description = "Deletes the task referred by it's id if found in user's tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        deleteTaskUseCase.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
