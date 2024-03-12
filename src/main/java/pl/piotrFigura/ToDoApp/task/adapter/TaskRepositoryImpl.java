package pl.piotrFigura.ToDoApp.task.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.piotrFigura.ToDoApp.task.Task;

@Repository
interface TaskRepositoryImpl extends TaskRepository, JpaRepository<Task, Long> {

    @Override
    @Query(nativeQuery = true, value = "select count(*)>0 from tasks where task_groups_id=:id and done=:isDone")
    boolean existsByTaskGroup(@Param("isDone") boolean isDone, @Param("id") Long groupId);
}
