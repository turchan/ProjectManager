package management.controller.interfaces;

import management.model.Project;
import management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends CrudRepository<Project, Long>
{

}
