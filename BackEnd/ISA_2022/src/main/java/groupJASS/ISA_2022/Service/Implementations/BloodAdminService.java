package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodAdmin;
import groupJASS.ISA_2022.Service.Interfaces.IBloodAdminService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class BloodAdminService implements IBloodAdminService {
    @Override
    public Iterable<BloodAdmin> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public BloodAdmin findById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public BloodAdmin save(BloodAdmin entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(UUID id) {
        throw new NotImplementedException();
    }
}
