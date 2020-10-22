import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSharedModule } from 'app/shared/shared.module';
import { EnrolleeComponent } from './enrollee.component';
import { EnrolleeDetailComponent } from './enrollee-detail.component';
import { EnrolleeUpdateComponent } from './enrollee-update.component';
import { EnrolleeDeleteDialogComponent } from './enrollee-delete-dialog.component';
import { enrolleeRoute } from './enrollee.route';

@NgModule({
  imports: [HealthSharedModule, RouterModule.forChild(enrolleeRoute)],
  declarations: [EnrolleeComponent, EnrolleeDetailComponent, EnrolleeUpdateComponent, EnrolleeDeleteDialogComponent],
  entryComponents: [EnrolleeDeleteDialogComponent],
})
export class HealthEnrolleeModule {}
