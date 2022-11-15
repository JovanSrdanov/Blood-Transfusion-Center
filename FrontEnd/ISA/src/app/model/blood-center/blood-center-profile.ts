import { Address } from './../address/address';
import { appointmentBloodCenterInfo } from './../appointment/appointment-blood-center-info';
import { staffMemberAppointmentInfo } from '../staff-members/staff-member-appointment-info';
export interface bloodCenterInfo {
  id: string;
  name: string;
  address: Address;
  description: string;
  rating: number;
  appointments: appointmentBloodCenterInfo;
  staff: staffMemberAppointmentInfo;
}
