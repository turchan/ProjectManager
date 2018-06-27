package management.controller.interfaces;

import management.model.TaskPriority;
import org.springframework.data.repository.CrudRepository;

public interface TaskPriorityRepository extends CrudRepository<TaskPriority, String> {
}
