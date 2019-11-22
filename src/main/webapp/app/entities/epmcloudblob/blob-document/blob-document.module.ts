import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { BlobDocumentComponent } from './blob-document.component';
import { BlobDocumentDetailComponent } from './blob-document-detail.component';
import { BlobDocumentUpdateComponent } from './blob-document-update.component';
import { BlobDocumentDeletePopupComponent, BlobDocumentDeleteDialogComponent } from './blob-document-delete-dialog.component';
import { blobDocumentRoute, blobDocumentPopupRoute } from './blob-document.route';

const ENTITY_STATES = [...blobDocumentRoute, ...blobDocumentPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BlobDocumentComponent,
    BlobDocumentDetailComponent,
    BlobDocumentUpdateComponent,
    BlobDocumentDeleteDialogComponent,
    BlobDocumentDeletePopupComponent
  ],
  entryComponents: [BlobDocumentDeleteDialogComponent]
})
export class EpmcloudblobBlobDocumentModule {}
