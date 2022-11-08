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
import { EditProductComponent } from './edit-product/edit-product.component';
import { ProductCardComponent } from './product-card/product-card.component';
import { SearchProductsComponent } from './search-products/search-products.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CartProductComponent } from './cart-product/cart-product.component';
import { AddToCartComponent } from './add-to-cart/add-to-cart.component';
import { OrderHistoryComponent } from './order-history/order-history.component';

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
    EditProductComponent,
    ProductCardComponent,
    RemoveProductComponent,
    SearchProductsComponent,
    DashboardComponent,
    ShoppingCartComponent,
    CartProductComponent,
    AddToCartComponent,
    OrderHistoryComponent
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
