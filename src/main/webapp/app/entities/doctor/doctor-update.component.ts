import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDoctor, Doctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';

@Component({
  selector: 'jhi-doctor-update',
  templateUrl: './doctor-update.component.html',
})
export class DoctorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idCard: [],
    name: [],
    code: [],
    dateOfBirth: [],
    address: [],
    level: [],
    experience: [],
    type: [],
    academicLevel: [],
  });

  constructor(protected doctorService: DoctorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctor }) => {
      this.updateForm(doctor);
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
}
