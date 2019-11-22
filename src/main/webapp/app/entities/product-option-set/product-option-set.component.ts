import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
  selector: 'jhi-product-option-set',
  templateUrl: './product-option-set.component.html'
})
export class ProductOptionSetComponent implements OnInit, OnDestroy {
  productOptionSets: IProductOptionSet[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productOptionSetService: ProductOptionSetService,
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
      this.productOptionSetService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductOptionSet[]>) => res.ok),
          map((res: HttpResponse<IProductOptionSet[]>) => res.body)
        )
        .subscribe((res: IProductOptionSet[]) => (this.productOptionSets = res));
      return;
    }
    this.productOptionSetService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductOptionSet[]>) => res.ok),
        map((res: HttpResponse<IProductOptionSet[]>) => res.body)
      )
      .subscribe((res: IProductOptionSet[]) => {
        this.productOptionSets = res;
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
    this.registerChangeInProductOptionSets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductOptionSet) {
    return item.id;
  }

  registerChangeInProductOptionSets() {
    this.eventSubscriber = this.eventManager.subscribe('productOptionSetListModification', response => this.loadAll());
  }
}
