package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Address;
import groupJASS.ISA_2022.Model.BloodDonor;

public interface IBloodDonorService extends ICrudService<BloodDonor> {
    BloodDonor RegisterUser(BloodDonor map, Address address);
}
