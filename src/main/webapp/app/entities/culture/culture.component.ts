import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICulture } from 'app/shared/model/culture.model';
import { AccountService } from 'app/core/auth/account.service';
import { CultureService } from './culture.service';

@Component({
  selector: 'jhi-culture',
  templateUrl: './culture.component.html'
})
export class CultureComponent implements OnInit, OnDestroy {
  cultures: ICulture[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected cultureService: CultureService,
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
      this.cultureService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICulture[]>) => res.ok),
          map((res: HttpResponse<ICulture[]>) => res.body)
        )
        .subscribe((res: ICulture[]) => (this.cultures = res));
      return;
    }
    this.cultureService
      .query()
      .pipe(
        filter((res: HttpResponse<ICulture[]>) => res.ok),
        map((res: HttpResponse<ICulture[]>) => res.body)
      )
      .subscribe((res: ICulture[]) => {
        this.cultures = res;
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
    this.registerChangeInCultures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICulture) {
    return item.id;
  }

  registerChangeInCultures() {
    this.eventSubscriber = this.eventManager.subscribe('cultureListModification', response => this.loadAll());
  }
}
