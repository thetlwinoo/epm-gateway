import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCategory } from 'app/shared/model/product-category.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductCategoryService } from './product-category.service';

@Component({
  selector: 'jhi-product-category',
  templateUrl: './product-category.component.html'
})
export class ProductCategoryComponent implements OnInit, OnDestroy {
  productCategories: IProductCategory[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productCategoryService: ProductCategoryService,
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
      this.productCategoryService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductCategory[]>) => res.ok),
          map((res: HttpResponse<IProductCategory[]>) => res.body)
        )
        .subscribe((res: IProductCategory[]) => (this.productCategories = res));
      return;
    }
    this.productCategoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductCategory[]>) => res.ok),
        map((res: HttpResponse<IProductCategory[]>) => res.body)
      )
      .subscribe((res: IProductCategory[]) => {
        this.productCategories = res;
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
    this.registerChangeInProductCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductCategory) {
    return item.id;
  }

  registerChangeInProductCategories() {
    this.eventSubscriber = this.eventManager.subscribe('productCategoryListModification', response => this.loadAll());
  }
}
