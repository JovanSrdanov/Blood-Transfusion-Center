package groupJASS.ISA_2022.DTO.Loyalty;

import groupJASS.ISA_2022.Model.LoyaltyType;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoyaltyInfoDto {
    public String category;
    public List<CouponInfoDto> coupons;

    public LoyaltyInfoDto(LoyaltyType loyaltyType) {
        this.category = loyaltyType.getCategoryName();
        this.coupons =  new ArrayList<>(ObjectMapperUtils.mapAll(loyaltyType.getCoupons(), CouponInfoDto.class));
    }
}
