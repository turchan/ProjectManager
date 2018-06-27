package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.RoleRepository;
import management.controller.interfaces.RoleService;
import management.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public List<Role> findAll() {
        return Lists.newArrayList(roleRepository.findAll());
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }
}
