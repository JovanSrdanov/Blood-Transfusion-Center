import { AddressRegUserDTO } from "../Model/AddressRegUserDTO";
import { BloodUserDTO } from "./BloodUserDTO";
import { NonRegisteredUserInfoDTO } from "./NonRegisteredUserInfoDTO";
export interface RegisterNonRegisteredUserDTO{
  addressRegUserDTO:AddressRegUserDTO,
  nonRegisteredUserInfoDTO: NonRegisteredUserInfoDTO,
  bloodUserDTO: BloodUserDTO
}
