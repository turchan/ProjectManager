package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.RiskRepository;
import management.controller.interfaces.RiskService;
import management.model.Risk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("riskService")
public class RiskServiceImpl implements RiskService
{
    private RiskRepository riskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Risk> findAll() {
        return Lists.newArrayList(riskRepository.findAll());
    }

    @Override
    public Risk findById(Long id) {
        return riskRepository.findById(id).get();
    }

    @Override
    public Risk save(Risk risk) {
        return null;
    }

    @Override
    public void delete(Risk risk) {

    }

    @Autowired
    public void setRiskRepository(RiskRepository riskRepository)
    {
        this.riskRepository = riskRepository;
    }
}
