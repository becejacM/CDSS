import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosticProcessFormComponent } from './diagnostic-process-form.component';

describe('DiagnosticProcessFormComponent', () => {
  let component: DiagnosticProcessFormComponent;
  let fixture: ComponentFixture<DiagnosticProcessFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiagnosticProcessFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosticProcessFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
