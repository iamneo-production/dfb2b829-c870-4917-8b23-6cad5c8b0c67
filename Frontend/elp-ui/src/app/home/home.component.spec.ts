import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [SlickCarouselModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have a valid title', () => {
    const titleElement = fixture.nativeElement.querySelector('.title');
    expect(titleElement.textContent).toContain('Welcome to Our Home');
  });

  it('should display carousel items', () => {
    const carouselItems = fixture.nativeElement.querySelectorAll('.carousel-item');
    expect(carouselItems.length).toBeGreaterThan(0);
  });

  it('should have a valid footer', () => {
    const footerElement = fixture.nativeElement.querySelector('.footer');
    expect(footerElement.textContent).toContain('© 2023 Your Company');
  });

  // Add more test cases as needed
});
