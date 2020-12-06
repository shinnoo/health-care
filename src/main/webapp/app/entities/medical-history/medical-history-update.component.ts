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
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';
import { INurse } from 'app/shared/model/nurse.model';
import { NurseService } from 'app/entities/nurse/nurse.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';
import { IDisease } from 'app/shared/model/disease.model';
import { DiseaseService } from 'app/entities/disease/disease.service';
import { IMedicine } from 'app/shared/model/medicine.model';
import { MedicineService } from 'app/entities/medicine/medicine.service';

type SelectableEntity = IDoctor | INurse | IPatient | IDisease | IMedicine;

@Component({
  selector: 'jhi-medical-history-update',
  templateUrl: './medical-history-update.component.html',
})
export class MedicalHistoryUpdateComponent implements OnInit {
  isSaving = false;
  doctors: IDoctor[] = [];
  nurses: INurse[] = [];
  patients: IPatient[] = [];
  diseases: IDisease[] = [];
  medicines: IMedicine[] = [];

  editForm = this.fb.group({
    id: [],
    joinedAt: [],
    leavedAt: [],
    totalPrice: [],
    count: [],
    isPaid: [],
    createdBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    lastModifiedDate: [],
    doctorId: [],
    nurseId: [],
    patientId: [],
    diseaseId: [],
    medicineId: [],
  });

  constructor(
    protected medicalHistoryService: MedicalHistoryService,
    protected doctorService: DoctorService,
    protected nurseService: NurseService,
    protected patientService: PatientService,
    protected diseaseService: DiseaseService,
    protected medicineService: MedicineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalHistory }) => {
      if (!medicalHistory.id) {
        const today = moment().startOf('day');
        medicalHistory.joinedAt = today;
        medicalHistory.leavedAt = today;
        medicalHistory.createdDate = today;
        medicalHistory.lastModifiedDate = today;
      }

      this.updateForm(medicalHistory);

      this.doctorService.query().subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body || []));

      this.nurseService.query().subscribe((res: HttpResponse<INurse[]>) => (this.nurses = res.body || []));

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));

      this.diseaseService.query().subscribe((res: HttpResponse<IDisease[]>) => (this.diseases = res.body || []));

      this.medicineService.query().subscribe((res: HttpResponse<IMedicine[]>) => (this.medicines = res.body || []));
    });
  }

  updateForm(medicalHistory: IMedicalHistory): void {
    this.editForm.patchValue({
      id: medicalHistory.id,
      joinedAt: medicalHistory.joinedAt ? medicalHistory.joinedAt.format(DATE_TIME_FORMAT) : null,
      leavedAt: medicalHistory.leavedAt ? medicalHistory.leavedAt.format(DATE_TIME_FORMAT) : null,
      totalPrice: medicalHistory.totalPrice,
      count: medicalHistory.count,
      isPaid: medicalHistory.isPaid,
      createdBy: medicalHistory.createdBy,
      createdDate: medicalHistory.createdDate ? medicalHistory.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: medicalHistory.lastModifiedBy,
      lastModifiedDate: medicalHistory.lastModifiedDate ? medicalHistory.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      doctorId: medicalHistory.doctorId,
      nurseId: medicalHistory.nurseId,
      patientId: medicalHistory.patientId,
      diseaseId: medicalHistory.diseaseId,
      medicineId: medicalHistory.medicineId,
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
      count: this.editForm.get(['count'])!.value,
      isPaid: this.editForm.get(['isPaid'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      doctorId: this.editForm.get(['doctorId'])!.value,
      nurseId: this.editForm.get(['nurseId'])!.value,
      patientId: this.editForm.get(['patientId'])!.value,
      diseaseId: this.editForm.get(['diseaseId'])!.value,
      medicineId: this.editForm.get(['medicineId'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
