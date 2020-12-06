import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthCareSharedModule } from 'app/shared/shared.module';
import { NurseComponent } from './nurse.component';
import { NurseDetailComponent } from './nurse-detail.component';
import { NurseUpdateComponent } from './nurse-update.component';
import { NurseDeleteDialogComponent } from './nurse-delete-dialog.component';
import { nurseRoute } from './nurse.route';

@NgModule({
  imports: [HealthCareSharedModule, RouterModule.forChild(nurseRoute)],
  declarations: [NurseComponent, NurseDetailComponent, NurseUpdateComponent, NurseDeleteDialogComponent],
  entryComponents: [NurseDeleteDialogComponent],
})
export class HealthCareNurseModule {}
