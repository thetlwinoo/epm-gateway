import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { SupplierTransactionsService } from './supplier-transactions.service';

@Component({
  selector: 'jhi-supplier-transactions',
  templateUrl: './supplier-transactions.component.html'
})
export class SupplierTransactionsComponent implements OnInit, OnDestroy {
  supplierTransactions: ISupplierTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected supplierTransactionsService: SupplierTransactionsService,
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
      this.supplierTransactionsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISupplierTransactions[]>) => res.ok),
          map((res: HttpResponse<ISupplierTransactions[]>) => res.body)
        )
        .subscribe((res: ISupplierTransactions[]) => (this.supplierTransactions = res));
      return;
    }
    this.supplierTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISupplierTransactions[]>) => res.ok),
        map((res: HttpResponse<ISupplierTransactions[]>) => res.body)
      )
      .subscribe((res: ISupplierTransactions[]) => {
        this.supplierTransactions = res;
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
    this.registerChangeInSupplierTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierTransactions) {
    return item.id;
  }

  registerChangeInSupplierTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('supplierTransactionsListModification', response => this.loadAll());
  }
}
