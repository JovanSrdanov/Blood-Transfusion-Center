package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.Role;
import groupJASS.ISA_2022.Repository.RoleRepository;
import groupJASS.ISA_2022.Service.Interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }

    @Override
    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }

}
