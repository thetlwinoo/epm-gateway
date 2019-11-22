import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrency } from 'app/shared/model/currency.model';
import { AccountService } from 'app/core/auth/account.service';
import { CurrencyService } from './currency.service';

@Component({
  selector: 'jhi-currency',
  templateUrl: './currency.component.html'
})
export class CurrencyComponent implements OnInit, OnDestroy {
  currencies: ICurrency[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected currencyService: CurrencyService,
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
      this.currencyService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICurrency[]>) => res.ok),
          map((res: HttpResponse<ICurrency[]>) => res.body)
        )
        .subscribe((res: ICurrency[]) => (this.currencies = res));
      return;
    }
    this.currencyService
      .query()
      .pipe(
        filter((res: HttpResponse<ICurrency[]>) => res.ok),
        map((res: HttpResponse<ICurrency[]>) => res.body)
      )
      .subscribe((res: ICurrency[]) => {
        this.currencies = res;
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
    this.registerChangeInCurrencies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrency) {
    return item.id;
  }

  registerChangeInCurrencies() {
    this.eventSubscriber = this.eventManager.subscribe('currencyListModification', response => this.loadAll());
  }
}
