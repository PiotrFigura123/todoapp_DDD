package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.piotrFigura.ToDoApp.project.domain.Project;

import java.util.List;

interface ProjectQueryRepositoryImpl extends ProjectQueryRepository, Repository<Project, Long> {
    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();
}
