package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface TaskQueryGroupRepositoryImpl extends TaskQueryGroupRepository, JpaRepository<TaskGroups, Long> {
    @Override
    @Query("select distinct g from TaskGroups g join fetch g.tasks")
    List<TaskGroups> findAll();

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Long id);
}
