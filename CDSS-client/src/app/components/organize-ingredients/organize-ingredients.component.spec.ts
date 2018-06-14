import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizeIngredientsComponent } from './organize-ingredients.component';

describe('OrganizeIngredientsComponent', () => {
  let component: OrganizeIngredientsComponent;
  let fixture: ComponentFixture<OrganizeIngredientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizeIngredientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizeIngredientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
