package management.controller.interfaces;

import management.model.Project;
import management.model.Role;
import management.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProjectService
{
    List<Project> findAll();
    Project findById(Long id);
    Project save(Project project);
    void delete(Project project);
}
