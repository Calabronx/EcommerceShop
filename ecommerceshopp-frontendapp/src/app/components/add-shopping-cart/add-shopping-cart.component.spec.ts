import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddShoppingCartComponent } from './add-shopping-cart.component';

describe('AddShoppingCartComponent', () => {
  let component: AddShoppingCartComponent;
  let fixture: ComponentFixture<AddShoppingCartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddShoppingCartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddShoppingCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
