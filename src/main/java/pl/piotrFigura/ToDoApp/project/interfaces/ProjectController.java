package pl.piotrFigura.ToDoApp.project.interfaces;


import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;
import pl.piotrFigura.ToDoApp.project.domain.contract.ProjectWriteModel;
import pl.piotrFigura.ToDoApp.project.infrastructure.ProjectFacade;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectQueryRepository;

@Controller
@RequestMapping("/projects")
class ProjectController {
    private final ProjectFacade service;
    private final ProjectQueryRepository projectQueryRepository;

    ProjectController(final ProjectFacade service, final ProjectQueryRepository projectQueryRepository) {
        this.service = service;
        this.projectQueryRepository = projectQueryRepository;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }

    @ResponseBody
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Project>> readAllGroups() {
        return ResponseEntity.ok(projectQueryRepository.findAll());
    }
        @PostMapping
        String addProject(
            @ModelAttribute("project")
            @Valid @RequestBody ProjectWriteModel current,
            BindingResult bindingResult,
            Model model){
            if (bindingResult.hasErrors()){
                return "projects";
            }
            service.save(current);
            model.addAttribute("project", new ProjectWriteModel());
            model.addAttribute("projects", getProjects());
            model.addAttribute("message", "Dodano projekt");
            return "projects";
        }
        @PostMapping(params = "addStep")
        String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
            current.getSteps().add(new ProjectSteps());
            return "projects";
        }

        @PostMapping(params = "removeStep")
        String removeProjectStep(@ModelAttribute("project") ProjectWriteModel current){
            current.getSteps().remove(current.getSteps().size()-1);
            return "projects";
        }
        @Timed(value = "project.crate.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
        @PostMapping("/{id}")
        String createGroup(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable String id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ){
            try{
                service.createGroup(deadline, Long.valueOf(id));
                model.addAttribute("message", "Dodano grupe!");
            } catch (IllegalStateException | IllegalArgumentException e) {
                model.addAttribute("message", "Blad tworzenia grupy z projektu");
            }
            return "projects";
        }
        @ModelAttribute("projects")
        List<Project> getProjects(){
            return projectQueryRepository.findAll();
        }
    }
