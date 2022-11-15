import { AddressNoCoor } from "../address/address-no-coor";

export interface StaffBasicInfo {
    id: string;
    name: string;
    surname: string;
    phoneNumber: string;
    address: AddressNoCoor;
    email: string;

}
