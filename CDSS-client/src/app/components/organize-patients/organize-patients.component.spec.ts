import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizePatientsComponent } from './organize-patients.component';

describe('OrganizePatientsComponent', () => {
  let component: OrganizePatientsComponent;
  let fixture: ComponentFixture<OrganizePatientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizePatientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizePatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
