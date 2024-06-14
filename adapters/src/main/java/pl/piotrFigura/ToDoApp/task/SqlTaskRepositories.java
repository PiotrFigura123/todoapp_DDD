package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.util.List;

interface SqlTaskRepository extends TaskRepository, Repository<Task, Long> {
}

interface SqlQueryTaskRepository extends TaskQueryRepository, Repository<Task, Long> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Long id);
    List<TaskDto> findDtoByDone(@RequestParam boolean done);

}
