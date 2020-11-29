export interface IPatient {
  id?: number;
  idCard?: string;
  name?: string;
  code?: string;
  dateOfBirth?: string;
  address?: string;
  phoneNumber?: string;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public idCard?: string,
    public name?: string,
    public code?: string,
    public dateOfBirth?: string,
    public address?: string,
    public phoneNumber?: string
  ) {}
}
