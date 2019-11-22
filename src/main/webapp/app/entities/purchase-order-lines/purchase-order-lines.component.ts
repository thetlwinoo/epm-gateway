import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { AccountService } from 'app/core/auth/account.service';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';

@Component({
  selector: 'jhi-purchase-order-lines',
  templateUrl: './purchase-order-lines.component.html'
})
export class PurchaseOrderLinesComponent implements OnInit, OnDestroy {
  purchaseOrderLines: IPurchaseOrderLines[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
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
      this.purchaseOrderLinesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPurchaseOrderLines[]>) => res.ok),
          map((res: HttpResponse<IPurchaseOrderLines[]>) => res.body)
        )
        .subscribe((res: IPurchaseOrderLines[]) => (this.purchaseOrderLines = res));
      return;
    }
    this.purchaseOrderLinesService
      .query()
      .pipe(
        filter((res: HttpResponse<IPurchaseOrderLines[]>) => res.ok),
        map((res: HttpResponse<IPurchaseOrderLines[]>) => res.body)
      )
      .subscribe((res: IPurchaseOrderLines[]) => {
        this.purchaseOrderLines = res;
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
    this.registerChangeInPurchaseOrderLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPurchaseOrderLines) {
    return item.id;
  }

  registerChangeInPurchaseOrderLines() {
    this.eventSubscriber = this.eventManager.subscribe('purchaseOrderLinesListModification', response => this.loadAll());
  }
}
