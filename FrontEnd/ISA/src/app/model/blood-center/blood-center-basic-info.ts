import { Address } from "../address/address";

export interface BloodCenterBasicInfo{
    id: string;
    name: string;
    address: Address;
    rating: number;
}