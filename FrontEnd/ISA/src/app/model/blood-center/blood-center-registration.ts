import { Address } from "../address/address";
import { WorkingHours } from "../working-hours";

export interface BloodCenterRegistration{
    name: string;
    address: Address;  
    description: string;
    workingHours:WorkingHours;
}
