import { AddressNoCoor } from "../address/address-no-coor";

export interface BloodDonorInfo{
    id: string;
    email: string;
    name: string;
    surname: string;
    phoneNumber: string;
    institution: string;
    jmbg: string;
    gender: string;
    address: AddressNoCoor;
}