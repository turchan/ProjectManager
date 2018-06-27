package management.controller.interfaces;

import management.model.Task;

import java.util.List;

public interface TaskService
{
    List<Task> findAll();
    Task findById(Long id);
    Task save(Task task);
    void delete(Task task);
}
