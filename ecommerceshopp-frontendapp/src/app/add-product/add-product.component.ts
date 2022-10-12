import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Shoppingcart } from '../models/shoppingcart';
import { ShoppingcartService } from '../services/shoppingcart.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  id: number;
  product: Product = new Product();
  shoppingCart: Shoppingcart = new Shoppingcart();

  constructor(private _productService: ProductService, private _shoppCartService:
    ShoppingcartService,
    private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.addProduct(this.product, this.id);
  }

  addProduct(product: Product, id_cart: number) {
    this.id = this.route.snapshot.params['id'];
    id_cart = this.id;
    console.log("id route:"+this.id);
    console.log("this id :" + this.id);
    console.log("name product:" + product.id);
    console.log("id cart:" + id_cart);
    this._productService.addProduct(product, id_cart).subscribe(
      data => {
        console.log('response', data);
        this.router.navigateByUrl("/add-product/" + `/${id_cart}`);
      }
    )
  }

}
