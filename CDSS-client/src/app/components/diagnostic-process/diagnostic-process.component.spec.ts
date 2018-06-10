import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosticProcessComponent } from './diagnostic-process.component';

describe('DiagnosticProcessComponent', () => {
  let component: DiagnosticProcessComponent;
  let fixture: ComponentFixture<DiagnosticProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosticProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosticProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
