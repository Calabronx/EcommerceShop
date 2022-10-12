import { Component, OnInit } from '@angular/core';
import { Shoppingcart } from 'src/app/models/shoppingcart';
import { ShoppingcartService } from 'src/app/services/shoppingcart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-shopping-cart',
  templateUrl: './list-shopping-cart.component.html',
  styleUrls: ['./list-shopping-cart.component.css']
})
export class ListShoppingCartComponent implements OnInit {

  shoppingcart: Shoppingcart[] = [];
  private shoppCart = new Shoppingcart();

  constructor(private _shoppingCartService: ShoppingcartService, private router: Router) { }

  ngOnInit(): void {
    this.listCarts();
    //this.shoppingCartDetails(this.shoppCart.id);
  }

  filters = {
    keyword: '',
    sortBy: 'Name'
  }

  listCarts() {
    this._shoppingCartService.getShoppingCarts().subscribe(
      data => this.shoppingcart = data
    )
  }

  shoppingCartDetails(id: number) {
    this.router.navigate(['shoppCart', id]);
  }

  

 // listCartById(id: number) {
   // this._shoppingCartService.getShoppingCartById(id).subscribe(
     // data => {
       // this.shoppingcart = data
        //console.log('found cart', data);
        
      //}
    //)
  //}
}
