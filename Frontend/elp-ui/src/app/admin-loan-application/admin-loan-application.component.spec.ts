import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLoanApplicationComponent } from './admin-loan-application.component';

describe('AdminLoanApplicationComponent', () => {
  let component: AdminLoanApplicationComponent;
  let fixture: ComponentFixture<AdminLoanApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminLoanApplicationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLoanApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
