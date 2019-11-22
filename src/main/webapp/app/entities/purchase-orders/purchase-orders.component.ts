import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { AccountService } from 'app/core/auth/account.service';
import { PurchaseOrdersService } from './purchase-orders.service';

@Component({
  selector: 'jhi-purchase-orders',
  templateUrl: './purchase-orders.component.html'
})
export class PurchaseOrdersComponent implements OnInit, OnDestroy {
  purchaseOrders: IPurchaseOrders[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected purchaseOrdersService: PurchaseOrdersService,
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
      this.purchaseOrdersService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPurchaseOrders[]>) => res.ok),
          map((res: HttpResponse<IPurchaseOrders[]>) => res.body)
        )
        .subscribe((res: IPurchaseOrders[]) => (this.purchaseOrders = res));
      return;
    }
    this.purchaseOrdersService
      .query()
      .pipe(
        filter((res: HttpResponse<IPurchaseOrders[]>) => res.ok),
        map((res: HttpResponse<IPurchaseOrders[]>) => res.body)
      )
      .subscribe((res: IPurchaseOrders[]) => {
        this.purchaseOrders = res;
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
    this.registerChangeInPurchaseOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPurchaseOrders) {
    return item.id;
  }

  registerChangeInPurchaseOrders() {
    this.eventSubscriber = this.eventManager.subscribe('purchaseOrdersListModification', response => this.loadAll());
  }
}
