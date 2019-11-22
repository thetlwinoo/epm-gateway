import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { AccountService } from 'app/core/auth/account.service';
import { PersonPhoneService } from './person-phone.service';

@Component({
  selector: 'jhi-person-phone',
  templateUrl: './person-phone.component.html'
})
export class PersonPhoneComponent implements OnInit, OnDestroy {
  personPhones: IPersonPhone[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected personPhoneService: PersonPhoneService,
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
      this.personPhoneService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPersonPhone[]>) => res.ok),
          map((res: HttpResponse<IPersonPhone[]>) => res.body)
        )
        .subscribe((res: IPersonPhone[]) => (this.personPhones = res));
      return;
    }
    this.personPhoneService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonPhone[]>) => res.ok),
        map((res: HttpResponse<IPersonPhone[]>) => res.body)
      )
      .subscribe((res: IPersonPhone[]) => {
        this.personPhones = res;
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
    this.registerChangeInPersonPhones();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonPhone) {
    return item.id;
  }

  registerChangeInPersonPhones() {
    this.eventSubscriber = this.eventManager.subscribe('personPhoneListModification', response => this.loadAll());
  }
}
