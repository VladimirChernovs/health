import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSharedModule } from 'app/shared/shared.module';
import { DependentComponent } from './dependent.component';
import { DependentDetailComponent } from './dependent-detail.component';
import { DependentUpdateComponent } from './dependent-update.component';
import { DependentDeleteDialogComponent } from './dependent-delete-dialog.component';
import { dependentRoute } from './dependent.route';

@NgModule({
  imports: [HealthSharedModule, RouterModule.forChild(dependentRoute)],
  declarations: [DependentComponent, DependentDetailComponent, DependentUpdateComponent, DependentDeleteDialogComponent],
  entryComponents: [DependentDeleteDialogComponent],
})
export class HealthDependentModule {}
