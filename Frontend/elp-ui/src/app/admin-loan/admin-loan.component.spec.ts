import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLoanComponent } from './admin-loan.component';

describe('AdminLoanComponent', () => {
  let component: AdminLoanComponent;
  let fixture: ComponentFixture<AdminLoanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminLoanComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
