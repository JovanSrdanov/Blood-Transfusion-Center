import { Address } from "../address/address"

export interface BloodAdminRegistration{
    username: string,
    password: string,
    name: string,
    surname: string,
    phoneNumber: string
    address: Address
    email: string,
}