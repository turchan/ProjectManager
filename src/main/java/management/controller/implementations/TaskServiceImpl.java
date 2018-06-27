package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.TaskRepository;
import management.controller.interfaces.TaskService;
import management.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("taskService")
public class TaskServiceImpl implements TaskService
{
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAll() {
        return Lists.newArrayList(taskRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }
}
