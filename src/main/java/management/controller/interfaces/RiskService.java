package management.controller.interfaces;

import management.model.Risk;

import java.util.List;

public interface RiskService
{
    List<Risk> findAll();
    Risk findById(Long id);
    Risk save(Risk risk);
    void delete(Risk risk);
}
