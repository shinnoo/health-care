import { Moment } from 'moment';

export interface INurse {
  id?: number;
  idCard?: string;
  name?: string;
  code?: string;
  level?: string;
  experience?: string;
  dateOfBirth?: string;
  address?: string;
  phoneNumber?: string;
  salary?: number;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
}

export class Nurse implements INurse {
  constructor(
    public id?: number,
    public idCard?: string,
    public name?: string,
    public code?: string,
    public level?: string,
    public experience?: string,
    public dateOfBirth?: string,
    public address?: string,
    public phoneNumber?: string,
    public salary?: number,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment
  ) {}
}
