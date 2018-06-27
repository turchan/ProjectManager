package management.controller.interfaces;

import management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService
{
    List<User> findAll();
    List<User> findAllByEmail(String email);
    User findByEmail(String email);
    User findById(Long id);
    User save(User user);
    void delete(User user);
}
