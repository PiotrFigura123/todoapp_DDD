package pl.piotrFigura.ToDoApp.task.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;

class TaskGroupFacadeTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone tasks")
    void toggleGroup() {
    //given
        var mockTaskFacade = mock(TaskFacade.class);
        when(mockTaskFacade.areUndoneTasksWithGroup(anyLong())).thenReturn( true );
        var toTest = new TaskGroupFacade(null, null, mockTaskFacade, taskQueryRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup( 1l));
        //then
        assertThat(exception)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("cant be close. All task mu");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        var mockTaskFacade = mock(TaskFacade.class);
        when(mockTaskFacade.areUndoneTasksWithGroup(anyLong())).thenReturn( false );
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyLong())).thenReturn(Optional.empty());
        var toTest = new TaskGroupFacade(mockTaskGroupRepository, null, mockTaskFacade, taskQueryRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup( 1l));
        //then
        assertThat(exception)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("TaskGroup with id ");
    }

    @Test
    @DisplayName("should save group and make toggle")
    void should_toggleGroup() {
        //given
        var mockTaskFacade = mock(TaskFacade.class);
        when(mockTaskFacade.areUndoneTasksWithGroup(anyLong())).thenReturn( false );
        var group = new TaskGroups();
        var beforeToggle = group.isDone();
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        var toTest = new TaskGroupFacade(mockTaskGroupRepository, null, mockTaskFacade, taskQueryRepository);
        //when
        toTest.toggleGroup(0l);
        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }
}