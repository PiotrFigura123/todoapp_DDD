package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.piotrFigura.ToDoApp.task.domain.Task;

import java.util.List;

interface TaskQueryRepositoryImpl extends TaskQueryRepository, JpaRepository<Task, Long> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Long id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

    @Override
    List<Task> findAllByGroup_Id(Long groupId);

}
