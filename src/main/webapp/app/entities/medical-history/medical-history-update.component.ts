import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMedicalHistory, MedicalHistory } from 'app/shared/model/medical-history.model';
import { MedicalHistoryService } from './medical-history.service';

@Component({
  selector: 'jhi-medical-history-update',
  templateUrl: './medical-history-update.component.html',
})
export class MedicalHistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    joinedAt: [],
    leavedAt: [],
    totalPrice: [],
  });

  constructor(protected medicalHistoryService: MedicalHistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalHistory }) => {
      if (!medicalHistory.id) {
        const today = moment().startOf('day');
        medicalHistory.joinedAt = today;
        medicalHistory.leavedAt = today;
      }

      this.updateForm(medicalHistory);
    });
  }

  updateForm(medicalHistory: IMedicalHistory): void {
    this.editForm.patchValue({
      id: medicalHistory.id,
      joinedAt: medicalHistory.joinedAt ? medicalHistory.joinedAt.format(DATE_TIME_FORMAT) : null,
      leavedAt: medicalHistory.leavedAt ? medicalHistory.leavedAt.format(DATE_TIME_FORMAT) : null,
      totalPrice: medicalHistory.totalPrice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalHistory = this.createFromForm();
    if (medicalHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalHistoryService.update(medicalHistory));
    } else {
      this.subscribeToSaveResponse(this.medicalHistoryService.create(medicalHistory));
    }
  }

  private createFromForm(): IMedicalHistory {
    return {
      ...new MedicalHistory(),
      id: this.editForm.get(['id'])!.value,
      joinedAt: this.editForm.get(['joinedAt'])!.value ? moment(this.editForm.get(['joinedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      leavedAt: this.editForm.get(['leavedAt'])!.value ? moment(this.editForm.get(['leavedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalHistory>>): void {
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
