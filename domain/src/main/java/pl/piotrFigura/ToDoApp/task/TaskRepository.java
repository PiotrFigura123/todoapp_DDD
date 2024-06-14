package pl.piotrFigura.ToDoApp.task;

import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.util.Optional;

interface TaskRepository {

    Optional<TaskDto> findDtoById(Long id);

    TaskDto save(TaskDto source);

    void deleteById(Long id);
}

