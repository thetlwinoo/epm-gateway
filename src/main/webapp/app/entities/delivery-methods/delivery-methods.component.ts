import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { AccountService } from 'app/core/auth/account.service';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
  selector: 'jhi-delivery-methods',
  templateUrl: './delivery-methods.component.html'
})
export class DeliveryMethodsComponent implements OnInit, OnDestroy {
  deliveryMethods: IDeliveryMethods[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
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
      this.deliveryMethodsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IDeliveryMethods[]>) => res.ok),
          map((res: HttpResponse<IDeliveryMethods[]>) => res.body)
        )
        .subscribe((res: IDeliveryMethods[]) => (this.deliveryMethods = res));
      return;
    }
    this.deliveryMethodsService
      .query()
      .pipe(
        filter((res: HttpResponse<IDeliveryMethods[]>) => res.ok),
        map((res: HttpResponse<IDeliveryMethods[]>) => res.body)
      )
      .subscribe((res: IDeliveryMethods[]) => {
        this.deliveryMethods = res;
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
    this.registerChangeInDeliveryMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  registerChangeInDeliveryMethods() {
    this.eventSubscriber = this.eventManager.subscribe('deliveryMethodsListModification', response => this.loadAll());
  }
}
