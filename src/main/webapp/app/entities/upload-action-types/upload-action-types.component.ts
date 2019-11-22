import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { AccountService } from 'app/core/auth/account.service';
import { UploadActionTypesService } from './upload-action-types.service';

@Component({
  selector: 'jhi-upload-action-types',
  templateUrl: './upload-action-types.component.html'
})
export class UploadActionTypesComponent implements OnInit, OnDestroy {
  uploadActionTypes: IUploadActionTypes[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected uploadActionTypesService: UploadActionTypesService,
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
      this.uploadActionTypesService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IUploadActionTypes[]>) => res.ok),
          map((res: HttpResponse<IUploadActionTypes[]>) => res.body)
        )
        .subscribe((res: IUploadActionTypes[]) => (this.uploadActionTypes = res));
      return;
    }
    this.uploadActionTypesService
      .query()
      .pipe(
        filter((res: HttpResponse<IUploadActionTypes[]>) => res.ok),
        map((res: HttpResponse<IUploadActionTypes[]>) => res.body)
      )
      .subscribe((res: IUploadActionTypes[]) => {
        this.uploadActionTypes = res;
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
    this.registerChangeInUploadActionTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUploadActionTypes) {
    return item.id;
  }

  registerChangeInUploadActionTypes() {
    this.eventSubscriber = this.eventManager.subscribe('uploadActionTypesListModification', response => this.loadAll());
  }
}
