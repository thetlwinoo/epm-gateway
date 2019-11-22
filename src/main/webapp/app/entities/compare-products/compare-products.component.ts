import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { AccountService } from 'app/core/auth/account.service';
import { CompareProductsService } from './compare-products.service';

@Component({
  selector: 'jhi-compare-products',
  templateUrl: './compare-products.component.html'
})
export class CompareProductsComponent implements OnInit, OnDestroy {
  compareProducts: ICompareProducts[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected compareProductsService: CompareProductsService,
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
      this.compareProductsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICompareProducts[]>) => res.ok),
          map((res: HttpResponse<ICompareProducts[]>) => res.body)
        )
        .subscribe((res: ICompareProducts[]) => (this.compareProducts = res));
      return;
    }
    this.compareProductsService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompareProducts[]>) => res.ok),
        map((res: HttpResponse<ICompareProducts[]>) => res.body)
      )
      .subscribe((res: ICompareProducts[]) => {
        this.compareProducts = res;
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
    this.registerChangeInCompareProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompareProducts) {
    return item.id;
  }

  registerChangeInCompareProducts() {
    this.eventSubscriber = this.eventManager.subscribe('compareProductsListModification', response => this.loadAll());
  }
}
