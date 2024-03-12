package pl.piotrFigura.ToDoApp.task.adapter;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import pl.piotrFigura.ToDoApp.task.Task;

public interface TaskRepository {

    Optional<Task> findById(Long id);

    Task save(Task source);

    List<Task> findAll();

    boolean existsById(Long id);

    boolean existsByTaskGroup(@Param("isDone") boolean isDone, @Param("id") Long groupId);
}
