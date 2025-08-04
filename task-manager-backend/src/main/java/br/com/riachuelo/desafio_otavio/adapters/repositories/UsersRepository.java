package br.com.riachuelo.desafio_otavio.adapters.repositories;

import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}
