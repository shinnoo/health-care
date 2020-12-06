import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IDisease {
  id?: number;
  name?: string;
  description?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  doctors?: IDoctor[];
}

export class Disease implements IDisease {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public doctors?: IDoctor[]
  ) {}
}
