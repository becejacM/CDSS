import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RcardComponent } from './rcard.component';

describe('RcardComponent', () => {
  let component: RcardComponent;
  let fixture: ComponentFixture<RcardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RcardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RcardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
