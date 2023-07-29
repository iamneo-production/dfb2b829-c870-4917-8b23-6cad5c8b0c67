import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoutConfirmationDialogComponent } from './logout-confirmation-dialog.component';

describe('LogoutConfirmationDialogComponent', () => {
  let component: LogoutConfirmationDialogComponent;
  let fixture: ComponentFixture<LogoutConfirmationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogoutConfirmationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogoutConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
