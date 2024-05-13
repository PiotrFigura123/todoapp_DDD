package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Repository
interface TaskGroupRepositoryImpl extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {

    @Override
//    @Query("from TaskGroups g join fetch g.tasks")
    List<TaskGroups> findAll();

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);

    boolean existsByDoneIsFalseAndProject_Id(Long id);
}
