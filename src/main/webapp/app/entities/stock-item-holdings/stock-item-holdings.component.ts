import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { AccountService } from 'app/core/auth/account.service';
import { StockItemHoldingsService } from './stock-item-holdings.service';

@Component({
  selector: 'jhi-stock-item-holdings',
  templateUrl: './stock-item-holdings.component.html'
})
export class StockItemHoldingsComponent implements OnInit, OnDestroy {
  stockItemHoldings: IStockItemHoldings[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected stockItemHoldingsService: StockItemHoldingsService,
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
      this.stockItemHoldingsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IStockItemHoldings[]>) => res.ok),
          map((res: HttpResponse<IStockItemHoldings[]>) => res.body)
        )
        .subscribe((res: IStockItemHoldings[]) => (this.stockItemHoldings = res));
      return;
    }
    this.stockItemHoldingsService
      .query()
      .pipe(
        filter((res: HttpResponse<IStockItemHoldings[]>) => res.ok),
        map((res: HttpResponse<IStockItemHoldings[]>) => res.body)
      )
      .subscribe((res: IStockItemHoldings[]) => {
        this.stockItemHoldings = res;
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
    this.registerChangeInStockItemHoldings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStockItemHoldings) {
    return item.id;
  }

  registerChangeInStockItemHoldings() {
    this.eventSubscriber = this.eventManager.subscribe('stockItemHoldingsListModification', response => this.loadAll());
  }
}
