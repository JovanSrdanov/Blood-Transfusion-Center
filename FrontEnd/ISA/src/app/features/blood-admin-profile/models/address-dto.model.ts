export class AddressDto {
  street: string = '';
  number: string = '';
  city: string = '';
  country: string = '';
  latitude: number = 0;
  longitude: number = 0;

  public constructor(obj?: any) {
    this.street = obj.street;
    this.number = obj.number;
    this.city = obj.city;
    this.country = obj.country;
    this.latitude = obj.latitude;
    this.longitude = obj.longitude;
  }
}
