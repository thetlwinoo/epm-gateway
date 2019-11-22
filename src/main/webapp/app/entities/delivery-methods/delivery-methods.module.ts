import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { DeliveryMethodsComponent } from './delivery-methods.component';
import { DeliveryMethodsDetailComponent } from './delivery-methods-detail.component';
import { DeliveryMethodsUpdateComponent } from './delivery-methods-update.component';
import { DeliveryMethodsDeletePopupComponent, DeliveryMethodsDeleteDialogComponent } from './delivery-methods-delete-dialog.component';
import { deliveryMethodsRoute, deliveryMethodsPopupRoute } from './delivery-methods.route';

const ENTITY_STATES = [...deliveryMethodsRoute, ...deliveryMethodsPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeliveryMethodsComponent,
    DeliveryMethodsDetailComponent,
    DeliveryMethodsUpdateComponent,
    DeliveryMethodsDeleteDialogComponent,
    DeliveryMethodsDeletePopupComponent
  ],
  entryComponents: [DeliveryMethodsDeleteDialogComponent]
})
export class EpmgatewayDeliveryMethodsModule {}
