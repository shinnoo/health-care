import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INurse, Nurse } from 'app/shared/model/nurse.model';
import { NurseService } from './nurse.service';
import { NurseComponent } from './nurse.component';
import { NurseDetailComponent } from './nurse-detail.component';
import { NurseUpdateComponent } from './nurse-update.component';

@Injectable({ providedIn: 'root' })
export class NurseResolve implements Resolve<INurse> {
  constructor(private service: NurseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INurse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nurse: HttpResponse<Nurse>) => {
          if (nurse.body) {
            return of(nurse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nurse());
  }
}

export const nurseRoute: Routes = [
  {
    path: '',
    component: NurseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Nurses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NurseDetailComponent,
    resolve: {
      nurse: NurseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Nurses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NurseUpdateComponent,
    resolve: {
      nurse: NurseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Nurses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NurseUpdateComponent,
    resolve: {
      nurse: NurseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Nurses',
    },
    canActivate: [UserRouteAccessService],
  },
];
