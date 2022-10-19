import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddProductsComponent } from './add-products/add-products.component'
import { MessagesComponent } from './messages/messages.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { ProductComponent } from './product/product.component';
import { ProductsListComponent } from './products-list/products-list.component';
import { RemoveProductComponent } from './remove-product/remove-product.component';
import { SearchProductsComponent } from './search-products/search-products.component';

@NgModule({
  declarations: [
    AppComponent,
    AddProductsComponent,
    MessagesComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    UserComponent,
    ProductComponent,
    ProductsListComponent,
    RemoveProductComponent,
    SearchProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
