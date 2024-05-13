package pl.piotrFigura.ToDoApp.project.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.piotrFigura.ToDoApp.config.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectRepository;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskGroupService;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;
    @Mock
    TaskGroupService taskGroupService;
    @Mock
    private TaskGroupRepository taskGroupRepository;
    @Mock
    private TaskConfigurationProperties config;

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_and_openGroupsExist_throwsIllegalStateException() {
        //given
        when(taskGroupRepository.existsByDoneIsFalseAndProject_Id(anyLong())).thenReturn(true);
        when(config.isAllowMultipleTaskFromTemplate()).thenReturn(false);
        var toTest = new ProjectService(repository, taskGroupRepository, config, taskGroupService);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0l));
        //then
        assertThat(exception)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("one undone group");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow multiple group and no project for given id")
    void createGroup_configOk_and_noProjects_throwsIllegalArgumentException() {
        //given
        when(config.isAllowMultipleTaskFromTemplate()).thenReturn(true);
        var toTest = new ProjectService(repository, taskGroupRepository, config, taskGroupService);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0l));
        //then
        assertThat(exception)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Project with given id not");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow multiple group and no project for given id")
    void createGroup_configOk_and_openProjects_throwsIllegalStateException() {
        //given
        when(taskGroupRepository.existsByDoneIsFalseAndProject_Id(anyLong())).thenReturn(false);
        when(config.isAllowMultipleTaskFromTemplate()).thenReturn(false);
        var toTest = new ProjectService(repository, taskGroupRepository, config, taskGroupService);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0l));
        //then
        assertThat(exception)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Project with give");
    }

    @Test
    @DisplayName("should create new group from project")
    void createGroup_configuration_and_project_createNewGroup() {
        //given
        var today = LocalDate.now().atStartOfDay();
        var project = projectWith("bar", Set.of(-1, -2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyLong()))
            .thenReturn(Optional.of(project));
        when(config.isAllowMultipleTaskFromTemplate()).thenReturn(true);
        InMemoryGroupRepository taskGroupRepository = inMemoryGroupRepository();
        var serviceWithInMemReop = new TaskGroupService(inMemoryGroupRepository(), null);
        var toTest = new ProjectService(mockRepository, taskGroupRepository, config, serviceWithInMemReop);
        //when
        GroupReadModel result = toTest.createGroup(today, 1l);
        //then
        assertThat(result.getDescription()).isEqualTo("bar");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
    }

    private InMemoryGroupRepository inMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements TaskGroupRepository {

        private Long index = 0l;
        private Map<Long, TaskGroups> map = new HashMap<>();

        public int count() {
            return map.values().size();
        }

        @Override
        public List<TaskGroups> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroups> findById(Long id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroups save(TaskGroups entity) {
            if (entity.getId() != null && entity.getId() == 0) {
                try {
                    var field = TaskGroups.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, ++index);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Long id) {
            return map.values().stream()
                .filter(group -> !group.isDone())
                .anyMatch(group -> group.getProject() != null && group.getProject().getId() == id);
        }
    }

    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline) {
        Set<ProjectSteps> steps = daysToDeadline.stream()
            .map(days -> {
                var step = mock(ProjectSteps.class);
                when(step.getDescription()).thenReturn("foo");
                when(step.getDaysToDeadline()).thenReturn(days);
                return step;
            }).collect(Collectors.toSet());
        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getSteps()).thenReturn(steps);
        return result;
    }

}