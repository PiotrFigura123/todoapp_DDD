package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import java.util.Optional;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import java.util.List;

interface ProjectRepository {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    Project save(Project entity);
}
