package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public interface TaskRepository {

    Optional<Task> findById(Long id);

    Task save(Task source);

    List<Task> findAll();

    boolean existsById(Long id);

    List<Task> findByDone(@RequestParam boolean done);

    List<Task> findAllByGroup_Id(Long groupId);
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);
}
