import { AddressNoCoor } from "../address/address-no-coor";

export interface BloodDonorInfo {
    "id": string,
    "name": string,
    "surname": string,
    "phoneNumber": string,
    "institution": string,
    "jmbg": string,
    "gender": string,
    "occupation": string,
    "address": AddressNoCoor,
    "points": number,
    "email": string
}