package pl.piotrFigura.ToDoApp.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
class TestConfiguration {

    @Bean
    @Primary
    @Profile("test")
    TaskRepository testRepo() {
        return new TaskRepository() {
            private Map<Long, TaskDto> tasks = new HashMap<>();

            @Override
            public Optional<TaskDto> findDtoById(final Long id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public TaskDto save(TaskDto entity) {
                Long key = (long) (tasks.size() + 1);
                try {
                    var field = Task.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                tasks.put(key, entity);
                return tasks.get(key);
            }

            @Override
            public void deleteById(final Long id) {
                tasks.remove(id);
            }

        };
    }
    @Bean
    @Primary
    @Profile("test")
    TaskQueryRepository testQueryRepo() {
        return new TaskQueryRepository() {
            private Map<Long, TaskDto> tasks = new HashMap<>();

            @Override
            public List<TaskDto> findAllBy() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public boolean existsById(final Long id) {
                return tasks.containsKey(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(final Long groupId) {
                return false;
            }

            @Override
            public List<TaskDto> findDtoByDone(final boolean done) {
                return null;
            }

            @Override
            public List<TaskDto> findDtoByGroup_Id(final Long groupId) {
                return List.of();
            }
        };
    }
}
