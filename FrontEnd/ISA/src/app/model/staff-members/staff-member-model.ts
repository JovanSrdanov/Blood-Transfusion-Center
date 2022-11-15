import { bloodCenterInfo } from './../blood-center/blood-center-profile';
export interface staffMemberModel {
  id: string;
  name: string;
  surname: string;
  phoneNumber: string;
  bloodCenter: bloodCenterInfo;
}
