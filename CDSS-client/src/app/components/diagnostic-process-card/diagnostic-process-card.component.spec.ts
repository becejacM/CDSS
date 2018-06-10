import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosticProcessCardComponent } from './diagnostic-process-card.component';

describe('DiagnosticProcessCardComponent', () => {
  let component: DiagnosticProcessCardComponent;
  let fixture: ComponentFixture<DiagnosticProcessCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosticProcessCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosticProcessCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
