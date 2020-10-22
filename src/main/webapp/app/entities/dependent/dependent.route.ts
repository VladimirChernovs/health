import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDependent, Dependent } from 'app/shared/model/dependent.model';
import { DependentService } from './dependent.service';
import { DependentComponent } from './dependent.component';
import { DependentDetailComponent } from './dependent-detail.component';
import { DependentUpdateComponent } from './dependent-update.component';

@Injectable({ providedIn: 'root' })
export class DependentResolve implements Resolve<IDependent> {
  constructor(private service: DependentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDependent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dependent: HttpResponse<Dependent>) => {
          if (dependent.body) {
            return of(dependent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dependent());
  }
}

export const dependentRoute: Routes = [
  {
    path: '',
    component: DependentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Dependents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DependentDetailComponent,
    resolve: {
      dependent: DependentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Dependents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DependentUpdateComponent,
    resolve: {
      dependent: DependentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Dependents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DependentUpdateComponent,
    resolve: {
      dependent: DependentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Dependents',
    },
    canActivate: [UserRouteAccessService],
  },
];
