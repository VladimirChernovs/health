import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnrollee, Enrollee } from 'app/shared/model/enrollee.model';
import { EnrolleeService } from './enrollee.service';
import { EnrolleeComponent } from './enrollee.component';
import { EnrolleeDetailComponent } from './enrollee-detail.component';
import { EnrolleeUpdateComponent } from './enrollee-update.component';

@Injectable({ providedIn: 'root' })
export class EnrolleeResolve implements Resolve<IEnrollee> {
  constructor(private service: EnrolleeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnrollee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enrollee: HttpResponse<Enrollee>) => {
          if (enrollee.body) {
            return of(enrollee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Enrollee());
  }
}

export const enrolleeRoute: Routes = [
  {
    path: '',
    component: EnrolleeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Enrollees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnrolleeDetailComponent,
    resolve: {
      enrollee: EnrolleeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Enrollees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnrolleeUpdateComponent,
    resolve: {
      enrollee: EnrolleeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Enrollees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnrolleeUpdateComponent,
    resolve: {
      enrollee: EnrolleeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Enrollees',
    },
    canActivate: [UserRouteAccessService],
  },
];
