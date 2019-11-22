import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPeople } from 'app/shared/model/people.model';
import { AccountService } from 'app/core/auth/account.service';
import { PeopleService } from './people.service';

@Component({
  selector: 'jhi-people',
  templateUrl: './people.component.html'
})
export class PeopleComponent implements OnInit, OnDestroy {
  people: IPeople[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected peopleService: PeopleService,
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
      this.peopleService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPeople[]>) => res.ok),
          map((res: HttpResponse<IPeople[]>) => res.body)
        )
        .subscribe((res: IPeople[]) => (this.people = res));
      return;
    }
    this.peopleService
      .query()
      .pipe(
        filter((res: HttpResponse<IPeople[]>) => res.ok),
        map((res: HttpResponse<IPeople[]>) => res.body)
      )
      .subscribe((res: IPeople[]) => {
        this.people = res;
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
    this.registerChangeInPeople();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPeople) {
    return item.id;
  }

  registerChangeInPeople() {
    this.eventSubscriber = this.eventManager.subscribe('peopleListModification', response => this.loadAll());
  }
}
