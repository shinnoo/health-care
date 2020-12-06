import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

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
    code: [null, []],
    level: [],
    experience: [],
    dateOfBirth: [],
    address: [],
    phoneNumber: [],
    salary: [],
    createdBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    lastModifiedDate: [],
  });

  constructor(protected nurseService: NurseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nurse }) => {
      if (!nurse.id) {
        const today = moment().startOf('day');
        nurse.createdDate = today;
        nurse.lastModifiedDate = today;
      }

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
      salary: nurse.salary,
      createdBy: nurse.createdBy,
      createdDate: nurse.createdDate ? nurse.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: nurse.lastModifiedBy,
      lastModifiedDate: nurse.lastModifiedDate ? nurse.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
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
      salary: this.editForm.get(['salary'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
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
