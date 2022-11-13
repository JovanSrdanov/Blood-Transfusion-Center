export class StaffDto {
  name: string = '';
  surname: string = '';

  public constructor(obj?: any) {
    this.name = obj.name;
    this.surname = obj.surname;
  }
}
