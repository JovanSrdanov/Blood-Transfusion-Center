package groupJASS.ISA_2022.Service.Implementations;

import groupJASS.ISA_2022.Exceptions.BadRequestException;
import groupJASS.ISA_2022.Model.Coupon;
import groupJASS.ISA_2022.Repository.CouponRepository;
import groupJASS.ISA_2022.Service.Interfaces.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
@Primary
public class CouponService implements ICouponService {

    private final CouponRepository _couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this._couponRepository = couponRepository;
    }

    @Override
    public Iterable<Coupon> findAll() {
        return this._couponRepository.findAll();
    }

    @Override
    public Coupon findById(UUID id) {
        if (_couponRepository.findById(id).isPresent()) {

            return _couponRepository.findById(id).get();
        }

        throw new NotFoundException("Loyalty type not found");
    }

    @Override
    public Coupon save(Coupon entity){
        return _couponRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        _couponRepository.deleteById(id);
    }
}
