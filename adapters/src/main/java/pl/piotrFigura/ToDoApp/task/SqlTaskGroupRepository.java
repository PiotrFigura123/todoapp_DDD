package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

interface SqlTaskGroupRepository extends TaskGroupRepository, Repository<TaskGroups, Long> {
}

interface SqlTaskQueryGroupRepository extends TaskQueryGroupRepository, Repository<TaskGroups, Long> {
    @Override
    @Query("select distinct g from TaskGroups g join fetch g.tasks")
    List<TaskGroups> findAll();
}

