import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { BlobPhotosComponent } from './blob-photos.component';
import { BlobPhotosDetailComponent } from './blob-photos-detail.component';
import { BlobPhotosUpdateComponent } from './blob-photos-update.component';
import { BlobPhotosDeletePopupComponent, BlobPhotosDeleteDialogComponent } from './blob-photos-delete-dialog.component';
import { blobPhotosRoute, blobPhotosPopupRoute } from './blob-photos.route';

const ENTITY_STATES = [...blobPhotosRoute, ...blobPhotosPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BlobPhotosComponent,
    BlobPhotosDetailComponent,
    BlobPhotosUpdateComponent,
    BlobPhotosDeleteDialogComponent,
    BlobPhotosDeletePopupComponent
  ],
  entryComponents: [BlobPhotosDeleteDialogComponent]
})
export class EpmcloudblobBlobPhotosModule {}
