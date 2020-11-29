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
    public academicLevel?: string
  ) {}
}
