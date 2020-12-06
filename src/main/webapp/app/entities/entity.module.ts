import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.HealthCareDoctorModule),
      },
      {
        path: 'nurse',
        loadChildren: () => import('./nurse/nurse.module').then(m => m.HealthCareNurseModule),
      },
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.HealthCarePatientModule),
      },
      {
        path: 'medical-history',
        loadChildren: () => import('./medical-history/medical-history.module').then(m => m.HealthCareMedicalHistoryModule),
      },
      {
        path: 'disease',
        loadChildren: () => import('./disease/disease.module').then(m => m.HealthCareDiseaseModule),
      },
      {
        path: 'medicine',
        loadChildren: () => import('./medicine/medicine.module').then(m => m.HealthCareMedicineModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HealthCareEntityModule {}
