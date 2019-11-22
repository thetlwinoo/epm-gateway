import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICountries } from 'app/shared/model/countries.model';
import { AccountService } from 'app/core/auth/account.service';
import { CountriesService } from './countries.service';

@Component({
  selector: 'jhi-countries',
  templateUrl: './countries.component.html'
})
export class CountriesComponent implements OnInit, OnDestroy {
  countries: ICountries[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected countriesService: CountriesService,
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
      this.countriesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICountries[]>) => res.ok),
          map((res: HttpResponse<ICountries[]>) => res.body)
        )
        .subscribe((res: ICountries[]) => (this.countries = res));
      return;
    }
    this.countriesService
      .query()
      .pipe(
        filter((res: HttpResponse<ICountries[]>) => res.ok),
        map((res: HttpResponse<ICountries[]>) => res.body)
      )
      .subscribe((res: ICountries[]) => {
        this.countries = res;
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
    this.registerChangeInCountries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICountries) {
    return item.id;
  }

  registerChangeInCountries() {
    this.eventSubscriber = this.eventManager.subscribe('countriesListModification', response => this.loadAll());
  }
}
