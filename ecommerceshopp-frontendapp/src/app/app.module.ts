import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";
import { RouterModule, Routes } from "@angular/router";
import { FormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { ListShoppingCartComponent } from './components/list-shopping-cart/list-shopping-cart.component';

import { ShoppingCartDetailsComponent } from './components/shopping-cart-details/shopping-cart-details.component';
import { AddShoppingCartComponent } from './components/add-shopping-cart/add-shopping-cart.component';
import { ProductComponent } from './product/product.component';
import { ListProductComponent } from './list-product/list-product.component';
import { AddProductComponent } from './add-product/add-product.component';


const routers: Routes = [
  { path: 'list-shopping-cart', component: ListShoppingCartComponent },
  { path: 'add-product/:id', component: AddProductComponent },
  { path: 'get-cart/:id', component: ShoppingCartDetailsComponent },
  
  { path: 'create', component: AddShoppingCartComponent },
  
  { path: 'list-product', component: ListProductComponent },
  { path: '', redirectTo: '/list-shopping-cart', pathMatch: 'full' }
];


@NgModule({
  declarations: [
    AppComponent,
    ListShoppingCartComponent,
    
    ShoppingCartDetailsComponent,
    AddShoppingCartComponent,
    ProductComponent,
    ListProductComponent,
    AddProductComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routers)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
