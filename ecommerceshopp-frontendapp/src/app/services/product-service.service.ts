import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product } from '../models/product'

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  
  private getAll: string = "http://localhost:8080/api/v2/get";
  private add: string = "http://localhost:8080/api/v1/addproduct";


  constructor(private _httpClient: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this._httpClient.get<Product[]>(this.getAll).pipe(
      map(response => response)
    )
  }

  addProduct(product: Product,id: number): Observable<Product> {
    return this._httpClient.post<Product>(`${this.add}/${id}`, Product);
  }


}
