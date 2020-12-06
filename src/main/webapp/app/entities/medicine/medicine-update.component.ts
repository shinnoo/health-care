import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMedicine, Medicine } from 'app/shared/model/medicine.model';
import { MedicineService } from './medicine.service';

@Component({
  selector: 'jhi-medicine-update',
  templateUrl: './medicine-update.component.html',
})
export class MedicineUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    price: [],
    createdBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    lastModifiedDate: [],
  });

  constructor(protected medicineService: MedicineService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicine }) => {
      if (!medicine.id) {
        const today = moment().startOf('day');
        medicine.createdDate = today;
        medicine.lastModifiedDate = today;
      }

      this.updateForm(medicine);
    });
  }

  updateForm(medicine: IMedicine): void {
    this.editForm.patchValue({
      id: medicine.id,
      name: medicine.name,
      price: medicine.price,
      createdBy: medicine.createdBy,
      createdDate: medicine.createdDate ? medicine.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: medicine.lastModifiedBy,
      lastModifiedDate: medicine.lastModifiedDate ? medicine.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicine = this.createFromForm();
    if (medicine.id !== undefined) {
      this.subscribeToSaveResponse(this.medicineService.update(medicine));
    } else {
      this.subscribeToSaveResponse(this.medicineService.create(medicine));
    }
  }

  private createFromForm(): IMedicine {
    return {
      ...new Medicine(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicine>>): void {
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
