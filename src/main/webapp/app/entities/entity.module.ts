import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'enrollee',
        loadChildren: () => import('./enrollee/enrollee.module').then(m => m.HealthEnrolleeModule),
      },
      {
        path: 'dependent',
        loadChildren: () => import('./dependent/dependent.module').then(m => m.HealthDependentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HealthEntityModule {}
