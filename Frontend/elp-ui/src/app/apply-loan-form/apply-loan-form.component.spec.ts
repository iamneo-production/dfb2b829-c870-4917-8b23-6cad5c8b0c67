import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyLoanFormComponent } from './apply-loan-form.component';

describe('ApplyLoanFormComponent', () => {
  let component: ApplyLoanFormComponent;
  let fixture: ComponentFixture<ApplyLoanFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplyLoanFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplyLoanFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
