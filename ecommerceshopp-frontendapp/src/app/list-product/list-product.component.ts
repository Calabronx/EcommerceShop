import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product-service.service';
import { Router } from '@angular/router';
import { Shoppingcart } from '../models/shoppingcart';


@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products: Product[] = [];
  shoppingCart: Shoppingcart = new Shoppingcart();

  constructor(private _productService: ProductService, private router: Router) { }

  ngOnInit(): void {
    this.listProducts();
  }

  filters = {
    keyword: '',
    sortBy: 'name'
  }

  listProducts() {
    this._productService.getAllProducts().subscribe(
      data => this.products = data)
  }

}
