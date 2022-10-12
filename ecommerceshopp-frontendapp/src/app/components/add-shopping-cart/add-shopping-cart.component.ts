import { Component, OnInit } from '@angular/core';
import { Shoppingcart } from 'src/app/models/shoppingcart';
import { ShoppingcartService } from 'src/app/services/shoppingcart.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-shopping-cart',
  templateUrl: './add-shopping-cart.component.html',
  styleUrls: ['./add-shopping-cart.component.css']
})
export class AddShoppingCartComponent implements OnInit {

  shoppingcart: Shoppingcart = new Shoppingcart();

  constructor(private _shoppingCartService: ShoppingcartService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.addShoppingCart();
  }

  addShoppingCart() {
    this._shoppingCartService.saveCart(this.shoppingcart).subscribe(
      data => {
        console.log('response', data);
        this._router.navigateByUrl("/create");
      }
    )
  }

}
