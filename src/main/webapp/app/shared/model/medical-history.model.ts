import { Moment } from 'moment';

export interface IMedicalHistory {
  id?: number;
  joinedAt?: Moment;
  leavedAt?: Moment;
  totalPrice?: number;
  count?: number;
  isPaid?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  doctorName?: string;
  doctorId?: number;
  nurseName?: string;
  nurseId?: number;
  patientName?: string;
  patientId?: number;
  diseaseName?: string;
  diseaseId?: number;
  medicineName?: string;
  medicineId?: number;
}

export class MedicalHistory implements IMedicalHistory {
  constructor(
    public id?: number,
    public joinedAt?: Moment,
    public leavedAt?: Moment,
    public totalPrice?: number,
    public count?: number,
    public isPaid?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public doctorName?: string,
    public doctorId?: number,
    public nurseName?: string,
    public nurseId?: number,
    public patientName?: string,
    public patientId?: number,
    public diseaseName?: string,
    public diseaseId?: number,
    public medicineName?: string,
    public medicineId?: number
  ) {
    this.isPaid = this.isPaid || false;
  }
}
