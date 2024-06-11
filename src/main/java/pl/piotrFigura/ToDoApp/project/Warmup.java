package pl.piotrFigura.ToDoApp.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectRepository;

@Component("warmupProjects")
@Slf4j
class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;

    Warmup(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      log.info("Application warmup after context refreshed");
        final String description = "Project1";
        if(!projectRepository.existsByDescription(description)){
            log.info("No required project found! Adding it!");
            var project = new Project();
            project.setDescription(description);
            project.addStep(new ProjectSteps("step3", 4,project));
            project.addStep(new ProjectSteps("step2", 3,project));
            project.addStep(new ProjectSteps("step1", 2,project));
        projectRepository.save(project);
        }
    }
}
