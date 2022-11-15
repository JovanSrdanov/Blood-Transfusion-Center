import { AddressRegUserDTO } from "../Model/AddressRegUserDTO";
import { BloodDonor } from "./BloodDonor";
import { NonRegisteredUserInfoDTO } from "./NonRegisteredUserInfoDTO";
export interface RegisterNonRegisteredUserDTO{
  addressRegUserDTO:AddressRegUserDTO,
  nonRegisteredUserInfoDTO: NonRegisteredUserInfoDTO,
  bloodUserDTO: BloodDonor
}
