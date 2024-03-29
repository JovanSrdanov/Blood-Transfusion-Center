import { AddressNoCoor } from "../address/address-no-coor"

export interface StaffRegistration {
    password: string,
    name: string,
    surname: string,
    phoneNumber: string
    address: AddressNoCoor
    email: string,
}
