import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICities } from 'app/shared/model/cities.model';
import { AccountService } from 'app/core/auth/account.service';
import { CitiesService } from './cities.service';

@Component({
  selector: 'jhi-cities',
  templateUrl: './cities.component.html'
})
export class CitiesComponent implements OnInit, OnDestroy {
  cities: ICities[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected citiesService: CitiesService,
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
      this.citiesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICities[]>) => res.ok),
          map((res: HttpResponse<ICities[]>) => res.body)
        )
        .subscribe((res: ICities[]) => (this.cities = res));
      return;
    }
    this.citiesService
      .query()
      .pipe(
        filter((res: HttpResponse<ICities[]>) => res.ok),
        map((res: HttpResponse<ICities[]>) => res.body)
      )
      .subscribe((res: ICities[]) => {
        this.cities = res;
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
    this.registerChangeInCities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICities) {
    return item.id;
  }

  registerChangeInCities() {
    this.eventSubscriber = this.eventManager.subscribe('citiesListModification', response => this.loadAll());
  }
}
