package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import pl.piotrFigura.ToDoApp.task.domain.TaskDto;

import java.util.Optional;

public interface TaskRepository {

    Optional<TaskDto> findDtoById(Long id);

    TaskDto save(TaskDto source);

    void deleteById(Long id);
}

