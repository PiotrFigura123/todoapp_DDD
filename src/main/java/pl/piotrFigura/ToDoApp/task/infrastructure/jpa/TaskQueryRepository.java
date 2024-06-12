package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrFigura.ToDoApp.task.domain.Task;

import java.util.List;

public interface TaskQueryRepository {
    List<Task> findAll();

    boolean existsById(Long id);

    List<Task> findByDone(@RequestParam boolean done);

    List<Task> findAllByGroup_Id(Long groupId);
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

}
