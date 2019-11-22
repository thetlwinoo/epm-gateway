import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { UploadTransactionsService } from './upload-transactions.service';

@Component({
  selector: 'jhi-upload-transactions',
  templateUrl: './upload-transactions.component.html'
})
export class UploadTransactionsComponent implements OnInit, OnDestroy {
  uploadTransactions: IUploadTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected uploadTransactionsService: UploadTransactionsService,
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
      this.uploadTransactionsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IUploadTransactions[]>) => res.ok),
          map((res: HttpResponse<IUploadTransactions[]>) => res.body)
        )
        .subscribe((res: IUploadTransactions[]) => (this.uploadTransactions = res));
      return;
    }
    this.uploadTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<IUploadTransactions[]>) => res.ok),
        map((res: HttpResponse<IUploadTransactions[]>) => res.body)
      )
      .subscribe((res: IUploadTransactions[]) => {
        this.uploadTransactions = res;
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
    this.registerChangeInUploadTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUploadTransactions) {
    return item.id;
  }

  registerChangeInUploadTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('uploadTransactionsListModification', response => this.loadAll());
  }
}
