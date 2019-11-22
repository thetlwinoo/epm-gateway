import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { ProductAttributeComponent } from './product-attribute.component';
import { ProductAttributeDetailComponent } from './product-attribute-detail.component';
import { ProductAttributeUpdateComponent } from './product-attribute-update.component';
import { ProductAttributeDeletePopupComponent, ProductAttributeDeleteDialogComponent } from './product-attribute-delete-dialog.component';
import { productAttributeRoute, productAttributePopupRoute } from './product-attribute.route';

const ENTITY_STATES = [...productAttributeRoute, ...productAttributePopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductAttributeComponent,
    ProductAttributeDetailComponent,
    ProductAttributeUpdateComponent,
    ProductAttributeDeleteDialogComponent,
    ProductAttributeDeletePopupComponent
  ],
  entryComponents: [ProductAttributeDeleteDialogComponent]
})
export class EpmgatewayProductAttributeModule {}
