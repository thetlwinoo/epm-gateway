import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';
import { BlobMixedDocumentService } from './blob-mixed-document.service';
import { BlobMixedDocumentComponent } from './blob-mixed-document.component';
import { BlobMixedDocumentDetailComponent } from './blob-mixed-document-detail.component';
import { BlobMixedDocumentUpdateComponent } from './blob-mixed-document-update.component';
import { BlobMixedDocumentDeletePopupComponent } from './blob-mixed-document-delete-dialog.component';
import { IBlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

@Injectable({ providedIn: 'root' })
export class BlobMixedDocumentResolve implements Resolve<IBlobMixedDocument> {
  constructor(private service: BlobMixedDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBlobMixedDocument> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BlobMixedDocument>) => response.ok),
        map((blobMixedDocument: HttpResponse<BlobMixedDocument>) => blobMixedDocument.body)
      );
    }
    return of(new BlobMixedDocument());
  }
}

export const blobMixedDocumentRoute: Routes = [
  {
    path: '',
    component: BlobMixedDocumentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobMixedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BlobMixedDocumentDetailComponent,
    resolve: {
      blobMixedDocument: BlobMixedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobMixedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BlobMixedDocumentUpdateComponent,
    resolve: {
      blobMixedDocument: BlobMixedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobMixedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BlobMixedDocumentUpdateComponent,
    resolve: {
      blobMixedDocument: BlobMixedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobMixedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blobMixedDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BlobMixedDocumentDeletePopupComponent,
    resolve: {
      blobMixedDocument: BlobMixedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmgatewayApp.epmcloudblobBlobMixedDocument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
