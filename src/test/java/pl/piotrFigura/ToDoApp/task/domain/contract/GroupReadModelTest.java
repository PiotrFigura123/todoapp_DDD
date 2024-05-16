package pl.piotrFigura.ToDoApp.task.domain.contract;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

class GroupReadModelTest {
    @Test
    @DisplayName("should create null deadline for group when no tesk deadlines")
    void constructor_noDeadlines_createNullDeadline(){
        //given
        var source = new TaskGroups();
        source.setDescription("foo");
        source.setTasks(Set.of(new Task("bar", null, null)));
        //when
        var result = new GroupReadModel(source);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }

}