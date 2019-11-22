import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';
import { BlobPhotosService } from './blob-photos.service';
import { BlobPhotosComponent } from './blob-photos.component';
import { BlobPhotosDetailComponent } from './blob-photos-detail.component';
import { BlobPhotosUpdateComponent } from './blob-photos-update.component';
import { BlobPhotosDeletePopupComponent } from './blob-photos-delete-dialog.component';
import { IBlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

@Injectable({ providedIn: 'root' })
export class BlobPhotosResolve implements Resolve<IBlobPhotos> {
  constructor(private service: BlobPhotosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBlobPhotos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BlobPhotos>) => response.ok),
        map((blobPhotos: HttpResponse<BlobPhotos>) => blobPhotos.body)
      );
    }
    return of(new BlobPhotos());
  }
}

export const blobPhotosRoute: Routes = [
  {
    path: '',
    component: BlobPhotosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobPhotos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BlobPhotosDetailComponent,
    resolve: {
      blobPhotos: BlobPhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobPhotos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BlobPhotosUpdateComponent,
    resolve: {
      blobPhotos: BlobPhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobPhotos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BlobPhotosUpdateComponent,
    resolve: {
      blobPhotos: BlobPhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobPhotos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blobPhotosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BlobPhotosDeletePopupComponent,
    resolve: {
      blobPhotos: BlobPhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobPhotos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
