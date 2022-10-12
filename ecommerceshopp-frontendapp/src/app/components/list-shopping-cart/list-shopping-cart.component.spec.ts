import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListShoppingCartComponent } from './list-shopping-cart.component';

describe('ListShoppingCartComponent', () => {
  let component: ListShoppingCartComponent;
  let fixture: ComponentFixture<ListShoppingCartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListShoppingCartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListShoppingCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
