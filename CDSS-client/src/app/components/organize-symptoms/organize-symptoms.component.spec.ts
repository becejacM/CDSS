import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizeSymptomsComponent } from './organize-symptoms.component';

describe('OrganizeSymptomsComponent', () => {
  let component: OrganizeSymptomsComponent;
  let fixture: ComponentFixture<OrganizeSymptomsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizeSymptomsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizeSymptomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
