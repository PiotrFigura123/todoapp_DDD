package pl.piotrFigura.ToDoApp.project;

import java.util.Optional;

public interface ProjectRepository {

    Optional<Project> findById(Long id);

    Project save(Project entity);
    }
