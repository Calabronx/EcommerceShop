import { Component, OnInit } from '@angular/core';
import { Shoppingcart } from '../../models/shoppingcart';
import { ShoppingcartService } from '../../services/shoppingcart.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from '../../models/product';


@Component({
  selector: 'app-shopping-cart-details',
  templateUrl: './shopping-cart-details.component.html',
  styleUrls: ['./shopping-cart-details.component.css']
})
export class ShoppingCartDetailsComponent implements OnInit {

  id: number;
  shoppingcart: Shoppingcart[] = [];
  shoppingobj = new Shoppingcart();
  product = new Product();


  constructor(private route: ActivatedRoute, private router: Router,
    private shoppingCartService: ShoppingcartService) { }

  ngOnInit(): void {
    this.getShoppingCart(this.id);
  }

  getShoppingCart(id: number) {
    
    this.id = this.route.snapshot.params['id'];
    console.log(this.id);
    this.shoppingCartService.getShoppingCartById(this.id)
      .subscribe(data => {
        console.log(data)
        var productList = data[0].productsList;
        var json = JSON.stringify(data[0].productsList);
        data[0].productsList = json;
        console.log(json);
        
        console.log(data);

        //this.shoppingcart[0].productString = json;
        

        console.log("product list: " + this.shoppingobj.productsList);

        this.shoppingcart = data;
      }, error => console.log("objeto errors: " + this.shoppingobj));
  }

  list() {
    this.router.navigate(['shoppingobj']);
  }

  addProduct() {
    this.id = this.route.snapshot.params['id'];
    console.log("running adproduct()");
    this.shoppingCartService.addProduct(this.product,this.id).subscribe(
      data => {
        console.log('response', data);
        this.router.navigateByUrl("/addproduct/" + this.id);
      }
    )
  }
}
      
  


