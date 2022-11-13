package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.BloodUser;
import groupJASS.ISA_2022.Model.RegisteredUser;

public interface IBloodUserService extends ICrudService<BloodUser> {
    void registerNewUser(BloodUser bloodUser);

    void registerRegisteredUser(BloodUser map, RegisteredUser registeredUser);
}
