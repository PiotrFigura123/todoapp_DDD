package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import pl.piotrFigura.ToDoApp.task.TaskRepository;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaskRepositoryImplTest implements TaskRepository {

    private Map<Long, TaskDto> tasks = new HashMap<>();
    @Override
    public Optional<TaskDto> findDtoById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public TaskDto save(TaskDto source) {
        return tasks.put(tasks.size() + 1l, source);
    }

    @Override
    public void deleteById(final Long id) {

    }


}