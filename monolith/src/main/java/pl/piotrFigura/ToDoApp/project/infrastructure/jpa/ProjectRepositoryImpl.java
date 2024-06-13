package pl.piotrFigura.ToDoApp.project.infrastructure.jpa;

import org.springframework.data.repository.Repository;
import pl.piotrFigura.ToDoApp.project.domain.Project;

interface ProjectRepositoryImpl extends ProjectRepository, Repository<Project, Long> {

}
