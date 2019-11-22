import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';
import { BlobDocumentService } from './blob-document.service';
import { BlobDocumentComponent } from './blob-document.component';
import { BlobDocumentDetailComponent } from './blob-document-detail.component';
import { BlobDocumentUpdateComponent } from './blob-document-update.component';
import { BlobDocumentDeletePopupComponent } from './blob-document-delete-dialog.component';
import { IBlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

@Injectable({ providedIn: 'root' })
export class BlobDocumentResolve implements Resolve<IBlobDocument> {
  constructor(private service: BlobDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBlobDocument> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BlobDocument>) => response.ok),
        map((blobDocument: HttpResponse<BlobDocument>) => blobDocument.body)
      );
    }
    return of(new BlobDocument());
  }
}

export const blobDocumentRoute: Routes = [
  {
    path: '',
    component: BlobDocumentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BlobDocumentDetailComponent,
    resolve: {
      blobDocument: BlobDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BlobDocumentUpdateComponent,
    resolve: {
      blobDocument: BlobDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BlobDocumentUpdateComponent,
    resolve: {
      blobDocument: BlobDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blobDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BlobDocumentDeletePopupComponent,
    resolve: {
      blobDocument: BlobDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobDocument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
