package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.BugRepository;
import management.controller.interfaces.BugService;
import management.model.Bug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("bugService")
public class BugServiceImpl implements BugService
{
    private BugRepository bugRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Bug> findAll() {
        return Lists.newArrayList(bugRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Bug findById(Long id) {
        return bugRepository.findById(id).get();
    }

    @Override
    public Bug save(Bug bug) {
        return bugRepository.save(bug);
    }

    @Override
    public void delete(Bug bug) {
        bugRepository.delete(bug);
    }

    @Autowired
    public void setBugRepository(BugRepository bugRepository)
    {
        this.bugRepository = bugRepository;
    }
}
