package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.piotrFigura.ToDoApp.task.domain.Task;

@Repository
interface TaskRepositoryImpl extends TaskRepository, JpaRepository<Task, Long> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Long id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

    @Override
    List<Task> findAllByGroup_Id(Long groupId);
}
