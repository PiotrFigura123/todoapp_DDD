package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import java.util.Optional;
import pl.piotrFigura.ToDoApp.project.domain.Project;

public interface ProjectRepository {

    Optional<Project> findById(Long id);

    Project save(Project entity);
    }
