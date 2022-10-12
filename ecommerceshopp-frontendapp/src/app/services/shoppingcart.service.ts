import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Shoppingcart } from '../models/shoppingcart'
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ShoppingcartService {

  private getById: string = "http://localhost:8080/api/v1/cartstatus";
  private create: string = "http://localhost:8080/api/v1/create";
  private getAll: string = "http://localhost:8080/api/v1/getallcarts";
  private add_product: string = "http://localhost:8080/api/v1/addproduct/";


  constructor(private _httpClient: HttpClient) { }

  getShoppingCarts(): Observable<Shoppingcart[]> {
    return this._httpClient.get<Shoppingcart[]>(this.getAll).pipe(
      map(response => response)
    )
  }

  getShoppingCart(id: number): Observable<any> {
    return this._httpClient.get(`${this.getById}/${id}`);
  }

  getShoppingCartById(id: number): Observable<Shoppingcart[]> {
    return this._httpClient.get<Shoppingcart[]>(`${this.getById}/${id}`).pipe(
      map(response => response)
    )
  }

  saveCart(shoppingCart: Shoppingcart): Observable<Shoppingcart> {
    return this._httpClient.post<Shoppingcart>(this.create, shoppingCart);
  }
  addProduct(product: Product, id_cart: number): Observable<Product> {
    return this._httpClient.post<Product>(`${this.add_product}}/${id_cart}`, Product);
  }
  
  

}
