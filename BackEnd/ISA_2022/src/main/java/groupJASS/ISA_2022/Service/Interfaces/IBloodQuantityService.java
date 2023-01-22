package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.CenterOutOfBloodException;
import groupJASS.ISA_2022.Model.BloodQuantity;

import java.util.UUID;

public interface IBloodQuantityService extends ICrudService<BloodQuantity>{
    BloodQuantity updateQuantity(UUID bloodId, int quantity) throws  CenterOutOfBloodException;
}
