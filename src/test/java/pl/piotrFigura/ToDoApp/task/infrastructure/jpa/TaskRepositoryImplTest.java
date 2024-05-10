package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public class TaskRepositoryImplTest implements TaskRepository{

    private Map<Long, Task> tasks = new HashMap<>();
    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(Task source) {
        return tasks.put(tasks.size() + 1l, source);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }

    @Override
    public boolean existsByTaskGroup(boolean isDone, Long groupId) {
        return false;
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return null;
    }

}