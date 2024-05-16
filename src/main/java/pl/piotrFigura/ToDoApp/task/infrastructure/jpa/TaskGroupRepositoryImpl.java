package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Repository
interface TaskGroupRepositoryImpl extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {

    @Override
    @Query("select distinct g from TaskGroups g join fetch g.tasks")
    List<TaskGroups> findAll();

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Long id);
}
