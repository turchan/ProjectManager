package management.controller.interfaces;

import management.model.TaskStatus;
import org.springframework.data.repository.CrudRepository;

public interface TaskStatusRepository extends CrudRepository<TaskStatus, String> {
}
