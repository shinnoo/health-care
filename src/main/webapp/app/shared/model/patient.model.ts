import { Moment } from 'moment';

export interface IPatient {
  id?: number;
  idCard?: string;
  name?: string;
  code?: string;
  dateOfBirth?: string;
  address?: string;
  phoneNumber?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public idCard?: string,
    public name?: string,
    public code?: string,
    public dateOfBirth?: string,
    public address?: string,
    public phoneNumber?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment
  ) {}
}
