import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomers } from 'app/shared/model/customers.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomersService } from './customers.service';

@Component({
  selector: 'jhi-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit, OnDestroy {
  customers: ICustomers[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected customersService: CustomersService,
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
      this.customersService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICustomers[]>) => res.ok),
          map((res: HttpResponse<ICustomers[]>) => res.body)
        )
        .subscribe((res: ICustomers[]) => (this.customers = res));
      return;
    }
    this.customersService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomers[]>) => res.ok),
        map((res: HttpResponse<ICustomers[]>) => res.body)
      )
      .subscribe((res: ICustomers[]) => {
        this.customers = res;
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
    this.registerChangeInCustomers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomers) {
    return item.id;
  }

  registerChangeInCustomers() {
    this.eventSubscriber = this.eventManager.subscribe('customersListModification', response => this.loadAll());
  }
}
