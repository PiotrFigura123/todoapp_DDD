package pl.piotrFigura.ToDoApp.project;


import java.util.List;

public interface ProjectQueryRepository {
    List<Project> findAll();

    boolean existsByDescription(String description);
}
