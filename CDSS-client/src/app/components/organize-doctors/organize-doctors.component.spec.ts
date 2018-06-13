import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizeDoctorsComponent } from './organize-doctors.component';

describe('OrganizeDoctorsComponent', () => {
  let component: OrganizeDoctorsComponent;
  let fixture: ComponentFixture<OrganizeDoctorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizeDoctorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizeDoctorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
