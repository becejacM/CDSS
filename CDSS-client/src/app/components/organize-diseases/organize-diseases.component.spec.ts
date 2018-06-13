import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizeDiseasesComponent } from './organize-diseases.component';

describe('OrganizeDiseasesComponent', () => {
  let component: OrganizeDiseasesComponent;
  let fixture: ComponentFixture<OrganizeDiseasesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizeDiseasesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizeDiseasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
