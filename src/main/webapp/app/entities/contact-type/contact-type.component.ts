import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IContactType } from 'app/shared/model/contact-type.model';
import { AccountService } from 'app/core/auth/account.service';
import { ContactTypeService } from './contact-type.service';

@Component({
  selector: 'jhi-contact-type',
  templateUrl: './contact-type.component.html'
})
export class ContactTypeComponent implements OnInit, OnDestroy {
  contactTypes: IContactType[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected contactTypeService: ContactTypeService,
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
      this.contactTypeService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IContactType[]>) => res.ok),
          map((res: HttpResponse<IContactType[]>) => res.body)
        )
        .subscribe((res: IContactType[]) => (this.contactTypes = res));
      return;
    }
    this.contactTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IContactType[]>) => res.ok),
        map((res: HttpResponse<IContactType[]>) => res.body)
      )
      .subscribe((res: IContactType[]) => {
        this.contactTypes = res;
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
    this.registerChangeInContactTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContactType) {
    return item.id;
  }

  registerChangeInContactTypes() {
    this.eventSubscriber = this.eventManager.subscribe('contactTypeListModification', response => this.loadAll());
  }
}
