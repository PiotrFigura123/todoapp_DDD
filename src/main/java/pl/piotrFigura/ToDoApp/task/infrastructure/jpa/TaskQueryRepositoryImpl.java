package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;

import java.util.List;

interface TaskQueryRepositoryImpl extends TaskQueryRepository, Repository<Task, Long> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Long id);

    @Override
    List<TaskDto> findAllBy();

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

    @Override
    List<TaskDto> findDtoByGroup_Id(Long groupId);

}
