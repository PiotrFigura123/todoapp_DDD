package pl.piotrFigura.ToDoApp.Thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;
import pl.piotrFigura.ToDoApp.project.domain.contract.ProjectWriteModel;
import pl.piotrFigura.ToDoApp.project.infrastructure.ProjectService;

@Controller
@RequestMapping("/projects")
class ProjectController {
    private final ProjectService service;

    ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());

        return "projects";
    }
    @PostMapping
    String addProject(@ModelAttribute("project") ProjectWriteModel current, Model model){
        service.create(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message", "Dodano projekt");
        return "projects";
    }
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }
    @ModelAttribute("projects")
    List<Project> getProjects(){
        return service.readAll();
    }
}
