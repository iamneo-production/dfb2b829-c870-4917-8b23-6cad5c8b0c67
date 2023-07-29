import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLoanUserIdComponent } from './add-loan-user-id.component';

describe('AddLoanUserIdComponent', () => {
  let component: AddLoanUserIdComponent;
  let fixture: ComponentFixture<AddLoanUserIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddLoanUserIdComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddLoanUserIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
