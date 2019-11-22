import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlists } from 'app/shared/model/wishlists.model';
import { AccountService } from 'app/core/auth/account.service';
import { WishlistsService } from './wishlists.service';

@Component({
  selector: 'jhi-wishlists',
  templateUrl: './wishlists.component.html'
})
export class WishlistsComponent implements OnInit, OnDestroy {
  wishlists: IWishlists[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected wishlistsService: WishlistsService,
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
      this.wishlistsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IWishlists[]>) => res.ok),
          map((res: HttpResponse<IWishlists[]>) => res.body)
        )
        .subscribe((res: IWishlists[]) => (this.wishlists = res));
      return;
    }
    this.wishlistsService
      .query()
      .pipe(
        filter((res: HttpResponse<IWishlists[]>) => res.ok),
        map((res: HttpResponse<IWishlists[]>) => res.body)
      )
      .subscribe((res: IWishlists[]) => {
        this.wishlists = res;
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
    this.registerChangeInWishlists();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWishlists) {
    return item.id;
  }

  registerChangeInWishlists() {
    this.eventSubscriber = this.eventManager.subscribe('wishlistsListModification', response => this.loadAll());
  }
}
