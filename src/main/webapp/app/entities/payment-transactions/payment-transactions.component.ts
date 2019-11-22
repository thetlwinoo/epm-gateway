import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { PaymentTransactionsService } from './payment-transactions.service';

@Component({
  selector: 'jhi-payment-transactions',
  templateUrl: './payment-transactions.component.html'
})
export class PaymentTransactionsComponent implements OnInit, OnDestroy {
  paymentTransactions: IPaymentTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected paymentTransactionsService: PaymentTransactionsService,
    protected dataUtils: JhiDataUtils,
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
      this.paymentTransactionsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPaymentTransactions[]>) => res.ok),
          map((res: HttpResponse<IPaymentTransactions[]>) => res.body)
        )
        .subscribe((res: IPaymentTransactions[]) => (this.paymentTransactions = res));
      return;
    }
    this.paymentTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaymentTransactions[]>) => res.ok),
        map((res: HttpResponse<IPaymentTransactions[]>) => res.body)
      )
      .subscribe((res: IPaymentTransactions[]) => {
        this.paymentTransactions = res;
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
    this.registerChangeInPaymentTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPaymentTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('paymentTransactionsListModification', response => this.loadAll());
  }
}
