import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactRecordComponent } from './contact-record.component';

describe('ContactRecordComponent', () => {
  let component: ContactRecordComponent;
  let fixture: ComponentFixture<ContactRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContactRecordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
