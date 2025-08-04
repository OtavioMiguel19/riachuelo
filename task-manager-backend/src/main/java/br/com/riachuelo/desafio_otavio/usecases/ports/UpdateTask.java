package br.com.riachuelo.desafio_otavio.usecases.ports;

import br.com.riachuelo.desafio_otavio.domains.Task;

public interface UpdateTask {
    Task update(long id, Task task);
}
