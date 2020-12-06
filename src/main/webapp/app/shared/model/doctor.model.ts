import { Moment } from 'moment';
import { IDisease } from 'app/shared/model/disease.model';

export interface IDoctor {
  id?: number;
  idCard?: string;
  name?: string;
  code?: string;
  dateOfBirth?: string;
  address?: string;
  level?: string;
  experience?: string;
  type?: string;
  academicLevel?: string;
  salary?: number;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  diseases?: IDisease[];
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public idCard?: string,
    public name?: string,
    public code?: string,
    public dateOfBirth?: string,
    public address?: string,
    public level?: string,
    public experience?: string,
    public type?: string,
    public academicLevel?: string,
    public salary?: number,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public diseases?: IDisease[]
  ) {}
}
