import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddProductsComponent } from './products/add-products/add-products.component'
import { MessagesComponent } from './messages/messages.component';
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
import { AdminStatsComponent } from './admin/admin-stats/admin-stats.component';
import { AdminAccountsComponent } from './admin/admin-accounts/admin-accounts.component';
import { ChangePasswordFormComponent } from './account/change-password-form/change-password-form.component';
import { DisplayUserInfoComponent } from './account/display-user-info/display-user-info.component';
import { DeleteAccountComponent } from './account/delete-account/delete-account.component';

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
    AdminEditComponent,
    AdminStatsComponent,
    AdminAccountsComponent,
    ChangePasswordFormComponent,
    DisplayUserInfoComponent,
    DeleteAccountComponent
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
