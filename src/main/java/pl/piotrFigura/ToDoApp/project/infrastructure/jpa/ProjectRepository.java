package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import java.util.List;
import java.util.Optional;
import pl.piotrFigura.ToDoApp.project.domain.Project;

public interface ProjectRepository {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    Project save(Project entity);

    boolean existsByDescription(String description);
}
