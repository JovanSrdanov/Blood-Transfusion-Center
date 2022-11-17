package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.Loyalty.LoyaltyInfoDto;
import groupJASS.ISA_2022.Service.Interfaces.ICouponService;
import groupJASS.ISA_2022.Service.Interfaces.ILoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("loyalty")
public class LoyaltyController {
    private final ILoyaltyService _loyaltyService;
    private final ICouponService _couponService;

    @Autowired
    public LoyaltyController(ILoyaltyService loyaltyService, ICouponService couponService) {
        this._loyaltyService = loyaltyService;
        this._couponService = couponService;
    }

    @GetMapping("get-by-points")
    public LoyaltyInfoDto getLoyaltyInfoByPoints(@PathParam("points") int points) {
        return new LoyaltyInfoDto(_loyaltyService.getLoyaltyByPoints(points));
    }
}
