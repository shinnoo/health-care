import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDisease, Disease } from 'app/shared/model/disease.model';
import { DiseaseService } from './disease.service';
import { DiseaseComponent } from './disease.component';
import { DiseaseDetailComponent } from './disease-detail.component';
import { DiseaseUpdateComponent } from './disease-update.component';

@Injectable({ providedIn: 'root' })
export class DiseaseResolve implements Resolve<IDisease> {
  constructor(private service: DiseaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisease> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((disease: HttpResponse<Disease>) => {
          if (disease.body) {
            return of(disease.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Disease());
  }
}

export const diseaseRoute: Routes = [
  {
    path: '',
    component: DiseaseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Diseases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiseaseDetailComponent,
    resolve: {
      disease: DiseaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diseases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiseaseUpdateComponent,
    resolve: {
      disease: DiseaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diseases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiseaseUpdateComponent,
    resolve: {
      disease: DiseaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diseases',
    },
    canActivate: [UserRouteAccessService],
  },
];
