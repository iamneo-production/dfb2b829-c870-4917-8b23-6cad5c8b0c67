import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLoanFormComponent } from './admin-loan-form.component';

describe('AdminLoanFormComponent', () => {
  let component: AdminLoanFormComponent;
  let fixture: ComponentFixture<AdminLoanFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminLoanFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLoanFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
