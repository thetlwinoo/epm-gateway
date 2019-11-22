import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLines } from 'app/shared/model/order-lines.model';
import { AccountService } from 'app/core/auth/account.service';
import { OrderLinesService } from './order-lines.service';

@Component({
  selector: 'jhi-order-lines',
  templateUrl: './order-lines.component.html'
})
export class OrderLinesComponent implements OnInit, OnDestroy {
  orderLines: IOrderLines[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected orderLinesService: OrderLinesService,
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
      this.orderLinesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IOrderLines[]>) => res.ok),
          map((res: HttpResponse<IOrderLines[]>) => res.body)
        )
        .subscribe((res: IOrderLines[]) => (this.orderLines = res));
      return;
    }
    this.orderLinesService
      .query()
      .pipe(
        filter((res: HttpResponse<IOrderLines[]>) => res.ok),
        map((res: HttpResponse<IOrderLines[]>) => res.body)
      )
      .subscribe((res: IOrderLines[]) => {
        this.orderLines = res;
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
    this.registerChangeInOrderLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrderLines) {
    return item.id;
  }

  registerChangeInOrderLines() {
    this.eventSubscriber = this.eventManager.subscribe('orderLinesListModification', response => this.loadAll());
  }
}
