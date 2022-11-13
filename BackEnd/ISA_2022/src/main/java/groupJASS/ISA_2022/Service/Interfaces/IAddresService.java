package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.Address;

public interface IAddresService extends ICrudService<Address> {
    Address AddresFromUserRegistration(Address map);
}
