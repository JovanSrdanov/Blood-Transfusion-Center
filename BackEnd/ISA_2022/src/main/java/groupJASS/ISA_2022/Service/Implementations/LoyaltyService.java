package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Model.LoyaltyType;
import groupJASS.ISA_2022.Repository.LoyaltyRepository;
import groupJASS.ISA_2022.Service.Interfaces.ILoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class LoyaltyService implements ILoyaltyService {
    private final LoyaltyRepository _loyaltyRepository;

    @Autowired
    public LoyaltyService(LoyaltyRepository _loyaltyRepository) {
        this._loyaltyRepository = _loyaltyRepository;
    }
    @Override
    public Iterable<LoyaltyType> findAll() {
        return _loyaltyRepository.findAll();
    }

    @Override
    public LoyaltyType findById(UUID id) {
        if (_loyaltyRepository.findById(id).isPresent()) {

            return _loyaltyRepository.findById(id).get();
        }

        throw new NotFoundException("Loyalty type not found");
    }

    @Override
    public LoyaltyType save(LoyaltyType entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        return _loyaltyRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _loyaltyRepository.deleteById(id);
    }


    @Override
    public LoyaltyType getLoyaltyByPoints(int points) {
        if(points < 0) points = 0;
        return _loyaltyRepository.findFirstByPointsReqLessThanEqualOrderByPointsReqDesc(points);
    }
}
