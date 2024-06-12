package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import pl.piotrFigura.ToDoApp.task.domain.Task;

import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(Long id);

    Task save(Task source);


    void deleteById(Long id);
}

