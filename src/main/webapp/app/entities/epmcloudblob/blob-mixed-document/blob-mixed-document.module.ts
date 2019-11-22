import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { BlobMixedDocumentComponent } from './blob-mixed-document.component';
import { BlobMixedDocumentDetailComponent } from './blob-mixed-document-detail.component';
import { BlobMixedDocumentUpdateComponent } from './blob-mixed-document-update.component';
import {
  BlobMixedDocumentDeletePopupComponent,
  BlobMixedDocumentDeleteDialogComponent
} from './blob-mixed-document-delete-dialog.component';
import { blobMixedDocumentRoute, blobMixedDocumentPopupRoute } from './blob-mixed-document.route';

const ENTITY_STATES = [...blobMixedDocumentRoute, ...blobMixedDocumentPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BlobMixedDocumentComponent,
    BlobMixedDocumentDetailComponent,
    BlobMixedDocumentUpdateComponent,
    BlobMixedDocumentDeleteDialogComponent,
    BlobMixedDocumentDeletePopupComponent
  ],
  entryComponents: [BlobMixedDocumentDeleteDialogComponent]
})
export class EpmcloudblobBlobMixedDocumentModule {}
