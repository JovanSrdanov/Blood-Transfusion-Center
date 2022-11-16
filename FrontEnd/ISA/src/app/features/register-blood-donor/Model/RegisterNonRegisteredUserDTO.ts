import { addressBloodDonorDTO } from "../Model/AddressRegUserDTO";
import { accountDTO } from "./accountDTO";
import { nonRegisteredBloodDonorInfoDTO } from "./NonRegisteredUserInfoDTO";
export interface RegisterNonRegisteredUserDTO{
  addressBloodDonorDTO:addressBloodDonorDTO,
  nonRegisteredBloodDonorInfoDTO: nonRegisteredBloodDonorInfoDTO,
  accountDTO: accountDTO
}
