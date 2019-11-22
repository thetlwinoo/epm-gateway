import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { AccountService } from 'app/core/auth/account.service';
import { ReviewsService } from './reviews.service';

@Component({
  selector: 'jhi-reviews',
  templateUrl: './reviews.component.html'
})
export class ReviewsComponent implements OnInit, OnDestroy {
  reviews: IReviews[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected reviewsService: ReviewsService,
    protected dataUtils: JhiDataUtils,
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
      this.reviewsService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IReviews[]>) => res.ok),
          map((res: HttpResponse<IReviews[]>) => res.body)
        )
        .subscribe((res: IReviews[]) => (this.reviews = res));
      return;
    }
    this.reviewsService
      .query()
      .pipe(
        filter((res: HttpResponse<IReviews[]>) => res.ok),
        map((res: HttpResponse<IReviews[]>) => res.body)
      )
      .subscribe((res: IReviews[]) => {
        this.reviews = res;
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
    this.registerChangeInReviews();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReviews) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInReviews() {
    this.eventSubscriber = this.eventManager.subscribe('reviewsListModification', response => this.loadAll());
  }
}
