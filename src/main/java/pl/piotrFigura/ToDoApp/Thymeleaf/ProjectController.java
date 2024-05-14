package pl.piotrFigura.ToDoApp.Thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.piotrFigura.ToDoApp.project.domain.contract.ProjectWriteModel;

@Controller
@RequestMapping("/projects")
class ProjectController {
    @GetMapping
    String showProjects(Model model) {


        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }

}
