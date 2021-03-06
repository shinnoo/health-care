import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthCareSharedModule } from 'app/shared/shared.module';
import { MedicineComponent } from './medicine.component';
import { MedicineDetailComponent } from './medicine-detail.component';
import { MedicineUpdateComponent } from './medicine-update.component';
import { MedicineDeleteDialogComponent } from './medicine-delete-dialog.component';
import { medicineRoute } from './medicine.route';

@NgModule({
  imports: [HealthCareSharedModule, RouterModule.forChild(medicineRoute)],
  declarations: [MedicineComponent, MedicineDetailComponent, MedicineUpdateComponent, MedicineDeleteDialogComponent],
  entryComponents: [MedicineDeleteDialogComponent],
})
export class HealthCareMedicineModule {}
