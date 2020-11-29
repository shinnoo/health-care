import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INurse, Nurse } from 'app/shared/model/nurse.model';
import { NurseService } from './nurse.service';

@Component({
  selector: 'jhi-nurse-update',
  templateUrl: './nurse-update.component.html',
})
export class NurseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idCard: [],
    name: [],
    code: [],
    level: [],
    experience: [],
    dateOfBirth: [],
    address: [],
    phoneNumber: [],
  });

  constructor(protected nurseService: NurseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nurse }) => {
      this.updateForm(nurse);
    });
  }

  updateForm(nurse: INurse): void {
    this.editForm.patchValue({
      id: nurse.id,
      idCard: nurse.idCard,
      name: nurse.name,
      code: nurse.code,
      level: nurse.level,
      experience: nurse.experience,
      dateOfBirth: nurse.dateOfBirth,
      address: nurse.address,
      phoneNumber: nurse.phoneNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nurse = this.createFromForm();
    if (nurse.id !== undefined) {
      this.subscribeToSaveResponse(this.nurseService.update(nurse));
    } else {
      this.subscribeToSaveResponse(this.nurseService.create(nurse));
    }
  }

  private createFromForm(): INurse {
    return {
      ...new Nurse(),
      id: this.editForm.get(['id'])!.value,
      idCard: this.editForm.get(['idCard'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      level: this.editForm.get(['level'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      address: this.editForm.get(['address'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INurse>>): void {
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
