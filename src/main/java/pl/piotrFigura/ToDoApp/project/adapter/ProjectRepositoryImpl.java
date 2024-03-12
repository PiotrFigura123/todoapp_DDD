package pl.piotrFigura.ToDoApp.project.adapter;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrFigura.ToDoApp.project.Project;

@Repository
interface ProjectRepositoryImpl extends ProjectRepository, JpaRepository<Project, Long> {

    @Override
    @Query("from Project p join fetch p.steps")
    List<Project> findAll();
}
