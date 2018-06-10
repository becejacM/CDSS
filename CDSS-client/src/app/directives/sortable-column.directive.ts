import {Directive, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';

import {SortService} from '../services/sort/sort.service';

@Directive({
  selector: '[appSortableColumn]'
})
export class SortableColumnDirective implements OnInit, OnDestroy {

  constructor(private sortService: SortService) {
  }

  @Output()
  sorted = new EventEmitter();

  private columnSortedSubscription: Subscription;

  ngOnInit() {
    // subscribe to sort changes so we emit and event for this data table
    this.columnSortedSubscription = this.sortService.columnSorted$.subscribe(event => {
      this.sorted.emit(event);
    });

  }

  ngOnDestroy() {
    this.columnSortedSubscription.unsubscribe();
  }

}
