import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { OrderLinesComponent } from './order-lines.component';
import { OrderLinesDetailComponent } from './order-lines-detail.component';
import { OrderLinesUpdateComponent } from './order-lines-update.component';
import { OrderLinesDeletePopupComponent, OrderLinesDeleteDialogComponent } from './order-lines-delete-dialog.component';
import { orderLinesRoute, orderLinesPopupRoute } from './order-lines.route';

const ENTITY_STATES = [...orderLinesRoute, ...orderLinesPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderLinesComponent,
    OrderLinesDetailComponent,
    OrderLinesUpdateComponent,
    OrderLinesDeleteDialogComponent,
    OrderLinesDeletePopupComponent
  ],
  entryComponents: [OrderLinesDeleteDialogComponent]
})
export class EpmgatewayOrderLinesModule {}
