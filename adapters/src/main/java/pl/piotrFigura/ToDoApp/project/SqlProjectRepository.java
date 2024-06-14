package pl.piotrFigura.ToDoApp.project;

import org.springframework.data.repository.Repository;

interface SqlProjectRepository extends ProjectRepository, Repository<Project, Long> {
}
