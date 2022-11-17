package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.LoyaltyType;

public interface ILoyaltyService extends ICrudService<LoyaltyType>{
    LoyaltyType getLoyaltyByPoints(int points);

}
