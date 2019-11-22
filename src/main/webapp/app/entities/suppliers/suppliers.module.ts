import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { SuppliersComponent } from './suppliers.component';
import { SuppliersDetailComponent } from './suppliers-detail.component';
import { SuppliersUpdateComponent } from './suppliers-update.component';
import { SuppliersDeletePopupComponent, SuppliersDeleteDialogComponent } from './suppliers-delete-dialog.component';
import { suppliersRoute, suppliersPopupRoute } from './suppliers.route';

const ENTITY_STATES = [...suppliersRoute, ...suppliersPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SuppliersComponent,
    SuppliersDetailComponent,
    SuppliersUpdateComponent,
    SuppliersDeleteDialogComponent,
    SuppliersDeletePopupComponent
  ],
  entryComponents: [SuppliersDeleteDialogComponent]
})
export class EpmgatewaySuppliersModule {}
