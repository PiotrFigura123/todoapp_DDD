package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import org.springframework.data.repository.Repository;
import pl.piotrFigura.ToDoApp.project.domain.Project;

import java.util.List;

public interface ProjectQueryRepository {
    List<Project> findAll();

    boolean existsByDescription(String description);
}
