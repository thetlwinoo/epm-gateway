import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { AccountService } from 'app/core/auth/account.service';
import { TransactionTypesService } from './transaction-types.service';

@Component({
  selector: 'jhi-transaction-types',
  templateUrl: './transaction-types.component.html'
})
export class TransactionTypesComponent implements OnInit, OnDestroy {
  transactionTypes: ITransactionTypes[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected transactionTypesService: TransactionTypesService,
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
      this.transactionTypesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ITransactionTypes[]>) => res.ok),
          map((res: HttpResponse<ITransactionTypes[]>) => res.body)
        )
        .subscribe((res: ITransactionTypes[]) => (this.transactionTypes = res));
      return;
    }
    this.transactionTypesService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionTypes[]>) => res.ok),
        map((res: HttpResponse<ITransactionTypes[]>) => res.body)
      )
      .subscribe((res: ITransactionTypes[]) => {
        this.transactionTypes = res;
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
    this.registerChangeInTransactionTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionTypes) {
    return item.id;
  }

  registerChangeInTransactionTypes() {
    this.eventSubscriber = this.eventManager.subscribe('transactionTypesListModification', response => this.loadAll());
  }
}
