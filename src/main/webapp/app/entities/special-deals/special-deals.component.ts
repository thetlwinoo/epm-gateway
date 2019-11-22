import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { AccountService } from 'app/core/auth/account.service';
import { SpecialDealsService } from './special-deals.service';

@Component({
  selector: 'jhi-special-deals',
  templateUrl: './special-deals.component.html'
})
export class SpecialDealsComponent implements OnInit, OnDestroy {
  specialDeals: ISpecialDeals[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected specialDealsService: SpecialDealsService,
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
      this.specialDealsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISpecialDeals[]>) => res.ok),
          map((res: HttpResponse<ISpecialDeals[]>) => res.body)
        )
        .subscribe((res: ISpecialDeals[]) => (this.specialDeals = res));
      return;
    }
    this.specialDealsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpecialDeals[]>) => res.ok),
        map((res: HttpResponse<ISpecialDeals[]>) => res.body)
      )
      .subscribe((res: ISpecialDeals[]) => {
        this.specialDeals = res;
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
    this.registerChangeInSpecialDeals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpecialDeals) {
    return item.id;
  }

  registerChangeInSpecialDeals() {
    this.eventSubscriber = this.eventManager.subscribe('specialDealsListModification', response => this.loadAll());
  }
}
