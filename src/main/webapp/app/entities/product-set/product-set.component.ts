import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSet } from 'app/shared/model/product-set.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductSetService } from './product-set.service';

@Component({
  selector: 'jhi-product-set',
  templateUrl: './product-set.component.html'
})
export class ProductSetComponent implements OnInit, OnDestroy {
  productSets: IProductSet[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected productSetService: ProductSetService,
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
      this.productSetService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IProductSet[]>) => res.ok),
          map((res: HttpResponse<IProductSet[]>) => res.body)
        )
        .subscribe((res: IProductSet[]) => (this.productSets = res));
      return;
    }
    this.productSetService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductSet[]>) => res.ok),
        map((res: HttpResponse<IProductSet[]>) => res.body)
      )
      .subscribe((res: IProductSet[]) => {
        this.productSets = res;
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
    this.registerChangeInProductSets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductSet) {
    return item.id;
  }

  registerChangeInProductSets() {
    this.eventSubscriber = this.eventManager.subscribe('productSetListModification', response => this.loadAll());
  }
}
