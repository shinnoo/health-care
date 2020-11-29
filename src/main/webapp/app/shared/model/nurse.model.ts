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
    public phoneNumber?: string
  ) {}
}
