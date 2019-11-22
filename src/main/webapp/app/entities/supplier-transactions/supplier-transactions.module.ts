import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmgatewaySharedModule } from 'app/shared/shared.module';
import { SupplierTransactionsComponent } from './supplier-transactions.component';
import { SupplierTransactionsDetailComponent } from './supplier-transactions-detail.component';
import { SupplierTransactionsUpdateComponent } from './supplier-transactions-update.component';
import {
  SupplierTransactionsDeletePopupComponent,
  SupplierTransactionsDeleteDialogComponent
} from './supplier-transactions-delete-dialog.component';
import { supplierTransactionsRoute, supplierTransactionsPopupRoute } from './supplier-transactions.route';

const ENTITY_STATES = [...supplierTransactionsRoute, ...supplierTransactionsPopupRoute];

@NgModule({
  imports: [EpmgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupplierTransactionsComponent,
    SupplierTransactionsDetailComponent,
    SupplierTransactionsUpdateComponent,
    SupplierTransactionsDeleteDialogComponent,
    SupplierTransactionsDeletePopupComponent
  ],
  entryComponents: [SupplierTransactionsDeleteDialogComponent]
})
export class EpmgatewaySupplierTransactionsModule {}
