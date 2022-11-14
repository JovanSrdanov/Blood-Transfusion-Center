import { Address } from "../address/address"
import { AddressNoCoor } from "../address/addressNoCoor"

export interface BloodAdminRegistration{
    username: string,
    password: string,
    name: string,
    surname: string,
    phoneNumber: string
    address: AddressNoCoor
    email: string,
}