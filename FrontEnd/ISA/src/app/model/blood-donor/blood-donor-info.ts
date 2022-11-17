import { Address } from "../address/address";

export interface BloodDonorInfo {
    "id": string,
    "name": string,
    "surname": string,
    "phoneNumber": string,
    "institution": string,
    "jmbg": string,
    "gender": string,
    "occupation": string,
    "address": Address,
    "points": number,
    "email": string
}