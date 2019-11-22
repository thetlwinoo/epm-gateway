import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
  selector: 'jhi-customer-categories',
  templateUrl: './customer-categories.component.html'
})
export class CustomerCategoriesComponent implements OnInit, OnDestroy {
  customerCategories: ICustomerCategories[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
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
      this.customerCategoriesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICustomerCategories[]>) => res.ok),
          map((res: HttpResponse<ICustomerCategories[]>) => res.body)
        )
        .subscribe((res: ICustomerCategories[]) => (this.customerCategories = res));
      return;
    }
    this.customerCategoriesService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerCategories[]>) => res.ok),
        map((res: HttpResponse<ICustomerCategories[]>) => res.body)
      )
      .subscribe((res: ICustomerCategories[]) => {
        this.customerCategories = res;
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
    this.registerChangeInCustomerCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerCategories) {
    return item.id;
  }

  registerChangeInCustomerCategories() {
    this.eventSubscriber = this.eventManager.subscribe('customerCategoriesListModification', response => this.loadAll());
  }
}
