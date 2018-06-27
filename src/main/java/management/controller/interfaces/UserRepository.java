package management.controller.interfaces;

import management.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findAllByEmail(String email);
    User findByEmail(String email);
}
