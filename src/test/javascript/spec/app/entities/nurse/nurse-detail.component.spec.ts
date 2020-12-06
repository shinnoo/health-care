import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthCareTestModule } from '../../../test.module';
import { NurseDetailComponent } from 'app/entities/nurse/nurse-detail.component';
import { Nurse } from 'app/shared/model/nurse.model';

describe('Component Tests', () => {
  describe('Nurse Management Detail Component', () => {
    let comp: NurseDetailComponent;
    let fixture: ComponentFixture<NurseDetailComponent>;
    const route = ({ data: of({ nurse: new Nurse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HealthCareTestModule],
        declarations: [NurseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NurseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NurseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nurse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nurse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
