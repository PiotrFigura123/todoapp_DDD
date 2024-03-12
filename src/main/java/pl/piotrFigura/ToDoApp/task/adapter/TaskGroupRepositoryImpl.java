package pl.piotrFigura.ToDoApp.task.adapter;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

@Repository
interface TaskGroupRepositoryImpl extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {

    List<TaskGroups> findAll();

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);
}
