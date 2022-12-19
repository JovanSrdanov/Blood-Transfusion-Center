package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Role;

import java.util.List;

public interface IRoleService {
    Role findById(Long id);

    List<Role> findByName(String name);


}
