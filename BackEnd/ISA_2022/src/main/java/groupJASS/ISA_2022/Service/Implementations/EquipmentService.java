package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Equipment;
import groupJASS.ISA_2022.Repository.EquipmentRepository;
import groupJASS.ISA_2022.Service.Interfaces.IEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class EquipmentService implements IEquipmentService {
    private final EquipmentRepository _equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this._equipmentRepository = equipmentRepository;
    }

    @Override
    public Iterable<Equipment> findAll() {
        return _equipmentRepository.findAll();
    }

    @Override
    public Equipment findById(UUID id) {
        if (_equipmentRepository.findById(id).isPresent()) {
            return _equipmentRepository.findById(id).get();
        }
        throw new NotFoundException("Equipment not found");
    }

    @Override
    public Equipment save(Equipment entity) {
        Equipment equipment;
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            equipment = _equipmentRepository.save(entity);
        }
        else {
            Equipment oldEquipment = findById(entity.getId());
            if (oldEquipment == null) {
                throw new NotFoundException("Equipment not found");
            }
            oldEquipment.update(entity);
            equipment = _equipmentRepository.save(oldEquipment);
        }

        return equipment;
    }

    @Override
    public void deleteById(UUID id) {
        _equipmentRepository.deleteById(id);
    }

    @Override
    public Equipment updateQuantity(UUID equipmentId, double quantity) {
        Equipment updatedEquipment = findById(equipmentId);
        if (updatedEquipment == null) {
            throw new NotFoundException("Equipment not found");
        }
        updatedEquipment.setQuantity(quantity);
        return save(updatedEquipment);
    }

    @Override
    public List<Equipment> findByCentre(UUID centreId) {
        return _equipmentRepository.findByCentre(centreId);
    }
}
