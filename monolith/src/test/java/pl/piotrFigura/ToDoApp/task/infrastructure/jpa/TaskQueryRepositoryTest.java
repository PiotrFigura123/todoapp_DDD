package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import pl.piotrFigura.ToDoApp.task.TaskQueryRepository;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskQueryRepositoryTest implements TaskQueryRepository {
        private Map<Long, TaskDto> tasks = new HashMap<>();
        @Override
        public List<TaskDto> findAllBy() {
            return new ArrayList<>(tasks.values());
        }

        @Override
        public boolean existsById(Long id) {
            return tasks.containsKey(id);
        }

        @Override
        public List<TaskDto> findDtoByDone(boolean done) {
            return null;
        }

        @Override
        public List<TaskDto> findDtoByGroup_Id(Long groupId) {
            return List.of();
        }

        @Override
        public boolean existsByDoneIsFalseAndGroup_Id(Long groupId) {
            return false;
        }
    }