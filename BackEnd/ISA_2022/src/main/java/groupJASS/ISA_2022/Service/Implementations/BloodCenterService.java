package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Service.Interfaces.IBloodCenterService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public class BloodCenterService implements IBloodCenterService {
    @Override
    public Iterable<BloodCenter> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public BloodCenter findById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public BloodCenter save(BloodCenter entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(UUID id) {
        throw new NotImplementedException();
    }
}
