import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDoctor, Doctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';
import { IDisease } from 'app/shared/model/disease.model';
import { DiseaseService } from 'app/entities/disease/disease.service';

@Component({
  selector: 'jhi-doctor-update',
  templateUrl: './doctor-update.component.html',
})
export class DoctorUpdateComponent implements OnInit {
  isSaving = false;
  diseases: IDisease[] = [];

  editForm = this.fb.group({
    id: [],
    idCard: [],
    name: [],
    code: [null, []],
    dateOfBirth: [],
    address: [],
    level: [],
    experience: [],
    type: [],
    academicLevel: [],
    salary: [],
    createdBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    lastModifiedDate: [],
    diseases: [],
  });

  constructor(
    protected doctorService: DoctorService,
    protected diseaseService: DiseaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctor }) => {
      if (!doctor.id) {
        const today = moment().startOf('day');
        doctor.createdDate = today;
        doctor.lastModifiedDate = today;
      }

      this.updateForm(doctor);

      this.diseaseService.query().subscribe((res: HttpResponse<IDisease[]>) => (this.diseases = res.body || []));
    });
  }

  updateForm(doctor: IDoctor): void {
    this.editForm.patchValue({
      id: doctor.id,
      idCard: doctor.idCard,
      name: doctor.name,
      code: doctor.code,
      dateOfBirth: doctor.dateOfBirth,
      address: doctor.address,
      level: doctor.level,
      experience: doctor.experience,
      type: doctor.type,
      academicLevel: doctor.academicLevel,
      salary: doctor.salary,
      createdBy: doctor.createdBy,
      createdDate: doctor.createdDate ? doctor.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: doctor.lastModifiedBy,
      lastModifiedDate: doctor.lastModifiedDate ? doctor.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      diseases: doctor.diseases,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctor = this.createFromForm();
    if (doctor.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorService.update(doctor));
    } else {
      this.subscribeToSaveResponse(this.doctorService.create(doctor));
    }
  }

  private createFromForm(): IDoctor {
    return {
      ...new Doctor(),
      id: this.editForm.get(['id'])!.value,
      idCard: this.editForm.get(['idCard'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      address: this.editForm.get(['address'])!.value,
      level: this.editForm.get(['level'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      type: this.editForm.get(['type'])!.value,
      academicLevel: this.editForm.get(['academicLevel'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      diseases: this.editForm.get(['diseases'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctor>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDisease): any {
    return item.id;
  }

  getSelected(selectedVals: IDisease[], option: IDisease): IDisease {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
