import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NurseService } from 'app/entities/nurse/nurse.service';
import { INurse, Nurse } from 'app/shared/model/nurse.model';

describe('Service Tests', () => {
  describe('Nurse Service', () => {
    let injector: TestBed;
    let service: NurseService;
    let httpMock: HttpTestingController;
    let elemDefault: INurse;
    let expectedResult: INurse | INurse[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NurseService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Nurse(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Nurse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Nurse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nurse', () => {
        const returnedFromService = Object.assign(
          {
            idCard: 'BBBBBB',
            name: 'BBBBBB',
            code: 'BBBBBB',
            level: 'BBBBBB',
            experience: 'BBBBBB',
            dateOfBirth: 'BBBBBB',
            address: 'BBBBBB',
            phoneNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nurse', () => {
        const returnedFromService = Object.assign(
          {
            idCard: 'BBBBBB',
            name: 'BBBBBB',
            code: 'BBBBBB',
            level: 'BBBBBB',
            experience: 'BBBBBB',
            dateOfBirth: 'BBBBBB',
            address: 'BBBBBB',
            phoneNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Nurse', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
