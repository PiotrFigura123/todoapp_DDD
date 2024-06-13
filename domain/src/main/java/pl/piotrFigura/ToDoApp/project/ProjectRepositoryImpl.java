package pl.piotrFigura.ToDoApp.project;

import org.springframework.data.repository.Repository;

interface ProjectRepositoryImpl extends ProjectRepository, Repository<Project, Long> {

}
