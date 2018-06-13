import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizeMedicinesComponent } from './organize-medicines.component';

describe('OrganizeMedicinesComponent', () => {
  let component: OrganizeMedicinesComponent;
  let fixture: ComponentFixture<OrganizeMedicinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizeMedicinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizeMedicinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
