import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AdminGuard } from './model/AdminGuard';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddProductsComponent } from './products/add-products/add-products.component';
import { MessagesComponent } from './admin/messages/messages.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { ProductComponent } from './products/product/product.component';
import { ProductsListComponent } from './products/products-list/products-list.component';
import { RemoveProductComponent } from './products/remove-product/remove-product.component';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ProductCardComponent } from './products/product-card/product-card.component';
import { SearchProductsComponent } from './products/search-products/search-products.component';
import { AdminEditComponent } from './admin/admin-edit/admin-edit.component';
import { AdminAccountsComponent } from './admin/admin-accounts/admin-accounts.component';
import { ChangePasswordFormComponent } from './account/change-password-form/change-password-form.component';
import { DisplayUserInfoComponent } from './account/display-user-info/display-user-info.component';
import { UserGuard } from 'src/app/model/UserGuard';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CartProductComponent } from './shopping-cart/cart-product/cart-product.component';
import { AddToCartComponent } from './shopping-cart/add-to-cart/add-to-cart.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { PaypalButtonComponent } from './checkout/paypal-button/paypal-button.component';
import { CardImageComponent } from './products/card-image/card-image.component';
import { OrderHistoryComponent } from './statistics/order-history/order-history.component';
import { CartImageComponent } from './shopping-cart/cart-image/cart-image.component';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    AddProductsComponent,
    MessagesComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    ProductComponent,
    ProductsListComponent,
    EditProductComponent,
    ProductCardComponent,
    RemoveProductComponent,
    SearchProductsComponent,
    AdminEditComponent,
    AdminAccountsComponent,
    ChangePasswordFormComponent,
    DisplayUserInfoComponent,
    ShoppingCartComponent,
    CartProductComponent,
    AddToCartComponent,
    StatisticsComponent,
    OrderHistoryComponent,
    CheckoutComponent,
    PaypalButtonComponent,
    CardImageComponent,
    UserComponent,
    CartImageComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [AdminGuard, UserGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
