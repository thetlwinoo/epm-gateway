import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductBrand } from 'app/shared/model/product-brand.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductBrandService } from './product-brand.service';

@Component({
  selector: 'jhi-product-brand',
  templateUrl: './product-brand.component.html'
})
export class ProductBrandComponent implements OnInit, OnDestroy {
  productBrands: IProductBrand[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productBrandService: ProductBrandService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.productBrandService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductBrand[]>) => res.ok),
          map((res: HttpResponse<IProductBrand[]>) => res.body)
        )
        .subscribe((res: IProductBrand[]) => (this.productBrands = res));
      return;
    }
    this.productBrandService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductBrand[]>) => res.ok),
        map((res: HttpResponse<IProductBrand[]>) => res.body)
      )
      .subscribe((res: IProductBrand[]) => {
        this.productBrands = res;
        this.currentSearch = '';
      });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductBrands();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductBrand) {
    return item.id;
  }

  registerChangeInProductBrands() {
    this.eventSubscriber = this.eventManager.subscribe('productBrandListModification', response => this.loadAll());
  }
}
