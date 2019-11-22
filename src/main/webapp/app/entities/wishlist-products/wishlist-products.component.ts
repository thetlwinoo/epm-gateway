import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';
import { AccountService } from 'app/core/auth/account.service';
import { WishlistProductsService } from './wishlist-products.service';

@Component({
  selector: 'jhi-wishlist-products',
  templateUrl: './wishlist-products.component.html'
})
export class WishlistProductsComponent implements OnInit, OnDestroy {
  wishlistProducts: IWishlistProducts[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected wishlistProductsService: WishlistProductsService,
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
      this.wishlistProductsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IWishlistProducts[]>) => res.ok),
          map((res: HttpResponse<IWishlistProducts[]>) => res.body)
        )
        .subscribe((res: IWishlistProducts[]) => (this.wishlistProducts = res));
      return;
    }
    this.wishlistProductsService
      .query()
      .pipe(
        filter((res: HttpResponse<IWishlistProducts[]>) => res.ok),
        map((res: HttpResponse<IWishlistProducts[]>) => res.body)
      )
      .subscribe((res: IWishlistProducts[]) => {
        this.wishlistProducts = res;
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
    this.registerChangeInWishlistProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWishlistProducts) {
    return item.id;
  }

  registerChangeInWishlistProducts() {
    this.eventSubscriber = this.eventManager.subscribe('wishlistProductsListModification', response => this.loadAll());
  }
}
