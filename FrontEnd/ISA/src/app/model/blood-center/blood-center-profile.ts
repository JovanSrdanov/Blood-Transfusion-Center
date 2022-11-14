import { appointmentBloodCenterInfo } from './../appointment/appointment-blood-center-info';
import { address } from './../address/address';
import { staffMemberAppointmentInfo } from '../staff-members/staff-member-appointment-info';
export interface bloodCenterInfo {
  id: string;
  name: string;
  address: address;
  description: string;
  rating: number;
  appointments: appointmentBloodCenterInfo;
  staff: staffMemberAppointmentInfo;
}
