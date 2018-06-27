package management.controller.implementations;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import management.controller.interfaces.SeverityRiskRepository;
import management.controller.interfaces.SeverityRiskService;
import management.model.SeverityRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
@Service("severityRiskService")
public class SeverityRiskServiceImpl implements SeverityRiskService
{
    private SeverityRiskRepository severityRiskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<SeverityRisk> findAll() {
        return Lists.newArrayList(severityRiskRepository.findAll());
    }

    @Autowired
    public void setSeverityRepository(SeverityRiskRepository severityRiskRepository)
    {
        this.severityRiskRepository = severityRiskRepository;
    }
}
