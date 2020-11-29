import { Moment } from 'moment';

export interface IMedicalHistory {
  id?: number;
  joinedAt?: Moment;
  leavedAt?: Moment;
  totalPrice?: number;
}

export class MedicalHistory implements IMedicalHistory {
  constructor(public id?: number, public joinedAt?: Moment, public leavedAt?: Moment, public totalPrice?: number) {}
}
