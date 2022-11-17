package groupJASS.ISA_2022.DTO.Loyalty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouponInfoDto {
    private String description;

    private int discount;
}
