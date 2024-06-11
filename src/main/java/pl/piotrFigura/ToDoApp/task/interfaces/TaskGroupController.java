package pl.piotrFigura.ToDoApp.task.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupTaskWriteModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskFacade;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskGroupFacade;
import pl.piotrFigura.ToDoApp.util.GlobalControllerAdviceProcessing;

import java.net.URI;
import java.util.List;

@GlobalControllerAdviceProcessing
@Controller
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskGroupFacade service;
    private final TaskFacade taskFacade;

    TaskGroupController(TaskGroupFacade service, final TaskFacade taskFacade) {
        this.service = service;
        this.taskFacade = taskFacade;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model){
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }
        @ResponseBody
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = service.crateGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @ResponseBody
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {

        return ResponseEntity.ok(service.readAll());
    }
    @ResponseBody
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<TaskDto>> readAllTasksFromGroup(@PathVariable Long id){
        return ResponseEntity.ok(taskFacade.findAllTasksWithGroupId(id));
    }
    @ResponseBody
    @Transactional //na poczatku BEGIN a na koniec COMMIT na bazie
    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> toggleGroup(@PathVariable Long id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
        @ModelAttribute("group") @Valid GroupWriteModel current,
        BindingResult bindingResult,
        Model model){
        if (bindingResult.hasErrors()){
            return "groups";
        }
        service.crateGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupe");
        return "groups";
    }


    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current){
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @PostMapping(params = "removeTask", produces = MediaType.TEXT_HTML_VALUE)
    String removeGroupTask(@ModelAttribute("group") GroupWriteModel current){
        current.getTasks().remove(current.getTasks().size()-1);
        return "groups";
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
    return service.readAll();
    }
}
