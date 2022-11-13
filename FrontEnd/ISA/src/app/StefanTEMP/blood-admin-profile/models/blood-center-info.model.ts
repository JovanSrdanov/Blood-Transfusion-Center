import { StaffDto } from './staff-dto.model';
import { AppointmentDto } from './appointment-dto.model';
import { AddressDto } from './address-dto.model';
export class BloodCenterInfoDto {
  name: string = '';
  address: AddressDto;
  description: string = '';
  score: number = 0;
  appointments: AppointmentDto;
  administrators: StaffDto;

  public constructor(obj?: any) {
    this.name = obj.name;
    this.address = obj.address;
    this.description = obj.description;
    this.score = obj.score;
    this.appointments = obj.appointments;
    this.administrators = obj.administrators;
  }
}
