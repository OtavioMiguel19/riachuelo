package br.com.riachuelo.desafio_otavio.adapters.repositories;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskModel, Long> {
    Optional<TaskModel> findByIdAndUserId(Long id, Long userId);

    List<TaskModel> findAllByUserIdOrderByIdAsc(Long userId);
}
