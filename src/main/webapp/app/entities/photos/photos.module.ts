import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { PhotosComponent } from './photos.component';
import { PhotosDetailComponent } from './photos-detail.component';
import { PhotosUpdateComponent } from './photos-update.component';
import { PhotosDeletePopupComponent, PhotosDeleteDialogComponent } from './photos-delete-dialog.component';
import { photosRoute, photosPopupRoute } from './photos.route';

const ENTITY_STATES = [...photosRoute, ...photosPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PhotosComponent, PhotosDetailComponent, PhotosUpdateComponent, PhotosDeleteDialogComponent, PhotosDeletePopupComponent],
  entryComponents: [PhotosDeleteDialogComponent]
})
export class EpmgatewayPhotosModule {}
