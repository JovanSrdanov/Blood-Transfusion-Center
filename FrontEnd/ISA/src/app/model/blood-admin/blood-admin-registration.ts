import { AddressNoCoor } from "../address/address-no-coor"

export interface BloodAdminRegistration{
    username: string,
    password: string,
    name: string,
    surname: string,
    phoneNumber: string
    address: AddressNoCoor
    email: string,
}