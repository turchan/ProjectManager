package management.controller.interfaces;

import management.model.Bug;

import java.util.List;

public interface BugService
{
    List<Bug> findAll();
    Bug findById(Long id);
    Bug save(Bug bug);
    void delete(Bug bug);
}
