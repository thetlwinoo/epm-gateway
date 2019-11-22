import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address-types',
        loadChildren: () => import('./address-types/address-types.module').then(m => m.EpmgatewayAddressTypesModule)
      },
      {
        path: 'culture',
        loadChildren: () => import('./culture/culture.module').then(m => m.EpmgatewayCultureModule)
      },
      {
        path: 'addresses',
        loadChildren: () => import('./addresses/addresses.module').then(m => m.EpmgatewayAddressesModule)
      },
      {
        path: 'business-entity-address',
        loadChildren: () =>
          import('./business-entity-address/business-entity-address.module').then(m => m.EpmgatewayBusinessEntityAddressModule)
      },
      {
        path: 'ship-method',
        loadChildren: () => import('./ship-method/ship-method.module').then(m => m.EpmgatewayShipMethodModule)
      },
      {
        path: 'person-email-address',
        loadChildren: () => import('./person-email-address/person-email-address.module').then(m => m.EpmgatewayPersonEmailAddressModule)
      },
      {
        path: 'person-phone',
        loadChildren: () => import('./person-phone/person-phone.module').then(m => m.EpmgatewayPersonPhoneModule)
      },
      {
        path: 'phone-number-type',
        loadChildren: () => import('./phone-number-type/phone-number-type.module').then(m => m.EpmgatewayPhoneNumberTypeModule)
      },
      {
        path: 'contact-type',
        loadChildren: () => import('./contact-type/contact-type.module').then(m => m.EpmgatewayContactTypeModule)
      },
      {
        path: 'business-entity-contact',
        loadChildren: () =>
          import('./business-entity-contact/business-entity-contact.module').then(m => m.EpmgatewayBusinessEntityContactModule)
      },
      {
        path: 'countries',
        loadChildren: () => import('./countries/countries.module').then(m => m.EpmgatewayCountriesModule)
      },
      {
        path: 'state-provinces',
        loadChildren: () => import('./state-provinces/state-provinces.module').then(m => m.EpmgatewayStateProvincesModule)
      },
      {
        path: 'cities',
        loadChildren: () => import('./cities/cities.module').then(m => m.EpmgatewayCitiesModule)
      },
      {
        path: 'system-parameters',
        loadChildren: () => import('./system-parameters/system-parameters.module').then(m => m.EpmgatewaySystemParametersModule)
      },
      {
        path: 'transaction-types',
        loadChildren: () => import('./transaction-types/transaction-types.module').then(m => m.EpmgatewayTransactionTypesModule)
      },
      {
        path: 'people',
        loadChildren: () => import('./people/people.module').then(m => m.EpmgatewayPeopleModule)
      },
      {
        path: 'delivery-methods',
        loadChildren: () => import('./delivery-methods/delivery-methods.module').then(m => m.EpmgatewayDeliveryMethodsModule)
      },
      {
        path: 'supplier-categories',
        loadChildren: () => import('./supplier-categories/supplier-categories.module').then(m => m.EpmgatewaySupplierCategoriesModule)
      },
      {
        path: 'suppliers',
        loadChildren: () => import('./suppliers/suppliers.module').then(m => m.EpmgatewaySuppliersModule)
      },
      {
        path: 'supplier-transactions',
        loadChildren: () => import('./supplier-transactions/supplier-transactions.module').then(m => m.EpmgatewaySupplierTransactionsModule)
      },
      {
        path: 'currency-rate',
        loadChildren: () => import('./currency-rate/currency-rate.module').then(m => m.EpmgatewayCurrencyRateModule)
      },
      {
        path: 'purchase-orders',
        loadChildren: () => import('./purchase-orders/purchase-orders.module').then(m => m.EpmgatewayPurchaseOrdersModule)
      },
      {
        path: 'purchase-order-lines',
        loadChildren: () => import('./purchase-order-lines/purchase-order-lines.module').then(m => m.EpmgatewayPurchaseOrderLinesModule)
      },
      {
        path: 'buying-groups',
        loadChildren: () => import('./buying-groups/buying-groups.module').then(m => m.EpmgatewayBuyingGroupsModule)
      },
      {
        path: 'customer-categories',
        loadChildren: () => import('./customer-categories/customer-categories.module').then(m => m.EpmgatewayCustomerCategoriesModule)
      },
      {
        path: 'customers',
        loadChildren: () => import('./customers/customers.module').then(m => m.EpmgatewayCustomersModule)
      },
      {
        path: 'customer-transactions',
        loadChildren: () => import('./customer-transactions/customer-transactions.module').then(m => m.EpmgatewayCustomerTransactionsModule)
      },
      {
        path: 'payment-transactions',
        loadChildren: () => import('./payment-transactions/payment-transactions.module').then(m => m.EpmgatewayPaymentTransactionsModule)
      },
      {
        path: 'invoice-lines',
        loadChildren: () => import('./invoice-lines/invoice-lines.module').then(m => m.EpmgatewayInvoiceLinesModule)
      },
      {
        path: 'invoices',
        loadChildren: () => import('./invoices/invoices.module').then(m => m.EpmgatewayInvoicesModule)
      },
      {
        path: 'order-lines',
        loadChildren: () => import('./order-lines/order-lines.module').then(m => m.EpmgatewayOrderLinesModule)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(m => m.EpmgatewayOrdersModule)
      },
      {
        path: 'special-deals',
        loadChildren: () => import('./special-deals/special-deals.module').then(m => m.EpmgatewaySpecialDealsModule)
      },
      {
        path: 'cold-room-temperatures',
        loadChildren: () =>
          import('./cold-room-temperatures/cold-room-temperatures.module').then(m => m.EpmgatewayColdRoomTemperaturesModule)
      },
      {
        path: 'package-types',
        loadChildren: () => import('./package-types/package-types.module').then(m => m.EpmgatewayPackageTypesModule)
      },
      {
        path: 'products',
        loadChildren: () => import('./products/products.module').then(m => m.EpmgatewayProductsModule)
      },
      {
        path: 'barcode-types',
        loadChildren: () => import('./barcode-types/barcode-types.module').then(m => m.EpmgatewayBarcodeTypesModule)
      },
      {
        path: 'stock-items',
        loadChildren: () => import('./stock-items/stock-items.module').then(m => m.EpmgatewayStockItemsModule)
      },
      {
        path: 'stock-item-temp',
        loadChildren: () => import('./stock-item-temp/stock-item-temp.module').then(m => m.EpmgatewayStockItemTempModule)
      },
      {
        path: 'upload-transactions',
        loadChildren: () => import('./upload-transactions/upload-transactions.module').then(m => m.EpmgatewayUploadTransactionsModule)
      },
      {
        path: 'upload-action-types',
        loadChildren: () => import('./upload-action-types/upload-action-types.module').then(m => m.EpmgatewayUploadActionTypesModule)
      },
      {
        path: 'stock-item-transactions',
        loadChildren: () =>
          import('./stock-item-transactions/stock-item-transactions.module').then(m => m.EpmgatewayStockItemTransactionsModule)
      },
      {
        path: 'stock-item-holdings',
        loadChildren: () => import('./stock-item-holdings/stock-item-holdings.module').then(m => m.EpmgatewayStockItemHoldingsModule)
      },
      {
        path: 'warranty-types',
        loadChildren: () => import('./warranty-types/warranty-types.module').then(m => m.EpmgatewayWarrantyTypesModule)
      },
      {
        path: 'product-attribute',
        loadChildren: () => import('./product-attribute/product-attribute.module').then(m => m.EpmgatewayProductAttributeModule)
      },
      {
        path: 'product-attribute-set',
        loadChildren: () => import('./product-attribute-set/product-attribute-set.module').then(m => m.EpmgatewayProductAttributeSetModule)
      },
      {
        path: 'product-option',
        loadChildren: () => import('./product-option/product-option.module').then(m => m.EpmgatewayProductOptionModule)
      },
      {
        path: 'product-option-set',
        loadChildren: () => import('./product-option-set/product-option-set.module').then(m => m.EpmgatewayProductOptionSetModule)
      },
      {
        path: 'product-choice',
        loadChildren: () => import('./product-choice/product-choice.module').then(m => m.EpmgatewayProductChoiceModule)
      },
      {
        path: 'product-set',
        loadChildren: () => import('./product-set/product-set.module').then(m => m.EpmgatewayProductSetModule)
      },
      {
        path: 'product-set-details',
        loadChildren: () => import('./product-set-details/product-set-details.module').then(m => m.EpmgatewayProductSetDetailsModule)
      },
      {
        path: 'product-document',
        loadChildren: () => import('./product-document/product-document.module').then(m => m.EpmgatewayProductDocumentModule)
      },
      {
        path: 'materials',
        loadChildren: () => import('./materials/materials.module').then(m => m.EpmgatewayMaterialsModule)
      },
      {
        path: 'dangerous-goods',
        loadChildren: () => import('./dangerous-goods/dangerous-goods.module').then(m => m.EpmgatewayDangerousGoodsModule)
      },
      {
        path: 'product-brand',
        loadChildren: () => import('./product-brand/product-brand.module').then(m => m.EpmgatewayProductBrandModule)
      },
      {
        path: 'product-category',
        loadChildren: () => import('./product-category/product-category.module').then(m => m.EpmgatewayProductCategoryModule)
      },
      {
        path: 'product-catalog',
        loadChildren: () => import('./product-catalog/product-catalog.module').then(m => m.EpmgatewayProductCatalogModule)
      },
      {
        path: 'currency',
        loadChildren: () => import('./currency/currency.module').then(m => m.EpmgatewayCurrencyModule)
      },
      {
        path: 'photos',
        loadChildren: () => import('./photos/photos.module').then(m => m.EpmgatewayPhotosModule)
      },
      {
        path: 'unit-measure',
        loadChildren: () => import('./unit-measure/unit-measure.module').then(m => m.EpmgatewayUnitMeasureModule)
      },
      {
        path: 'vehicle-temperatures',
        loadChildren: () => import('./vehicle-temperatures/vehicle-temperatures.module').then(m => m.EpmgatewayVehicleTemperaturesModule)
      },
      {
        path: 'shopping-carts',
        loadChildren: () => import('./shopping-carts/shopping-carts.module').then(m => m.EpmgatewayShoppingCartsModule)
      },
      {
        path: 'shopping-cart-items',
        loadChildren: () => import('./shopping-cart-items/shopping-cart-items.module').then(m => m.EpmgatewayShoppingCartItemsModule)
      },
      {
        path: 'wishlists',
        loadChildren: () => import('./wishlists/wishlists.module').then(m => m.EpmgatewayWishlistsModule)
      },
      {
        path: 'wishlist-products',
        loadChildren: () => import('./wishlist-products/wishlist-products.module').then(m => m.EpmgatewayWishlistProductsModule)
      },
      {
        path: 'compares',
        loadChildren: () => import('./compares/compares.module').then(m => m.EpmgatewayComparesModule)
      },
      {
        path: 'compare-products',
        loadChildren: () => import('./compare-products/compare-products.module').then(m => m.EpmgatewayCompareProductsModule)
      },
      {
        path: 'reviews',
        loadChildren: () => import('./reviews/reviews.module').then(m => m.EpmgatewayReviewsModule)
      },
      {
        path: 'review-lines',
        loadChildren: () => import('./review-lines/review-lines.module').then(m => m.EpmgatewayReviewLinesModule)
      },
      {
        path: 'product-tags',
        loadChildren: () => import('./product-tags/product-tags.module').then(m => m.EpmgatewayProductTagsModule)
      },
      {
        path: 'supplier-imported-document',
        loadChildren: () =>
          import('./supplier-imported-document/supplier-imported-document.module').then(m => m.EpmgatewaySupplierImportedDocumentModule)
      },
      {
        path: 'blob-photos',
        loadChildren: () => import('./epmcloudblob/blob-photos/blob-photos.module').then(m => m.EpmcloudblobBlobPhotosModule)
      },
      {
        path: 'blob-document',
        loadChildren: () => import('./epmcloudblob/blob-document/blob-document.module').then(m => m.EpmcloudblobBlobDocumentModule)
      },
      {
        path: 'blob-mixed-document',
        loadChildren: () =>
          import('./epmcloudblob/blob-mixed-document/blob-mixed-document.module').then(m => m.EpmcloudblobBlobMixedDocumentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EpmgatewayEntityModule {}
