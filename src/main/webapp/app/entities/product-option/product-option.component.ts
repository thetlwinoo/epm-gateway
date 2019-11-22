import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOption } from 'app/shared/model/product-option.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductOptionService } from './product-option.service';

@Component({
  selector: 'jhi-product-option',
  templateUrl: './product-option.component.html'
})
export class ProductOptionComponent implements OnInit, OnDestroy {
  productOptions: IProductOption[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productOptionService: ProductOptionService,
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
      this.productOptionService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductOption[]>) => res.ok),
          map((res: HttpResponse<IProductOption[]>) => res.body)
        )
        .subscribe((res: IProductOption[]) => (this.productOptions = res));
      return;
    }
    this.productOptionService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductOption[]>) => res.ok),
        map((res: HttpResponse<IProductOption[]>) => res.body)
      )
      .subscribe((res: IProductOption[]) => {
        this.productOptions = res;
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
    this.registerChangeInProductOptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductOption) {
    return item.id;
  }

  registerChangeInProductOptions() {
    this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', response => this.loadAll());
  }
}
