import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { AddProductsComponent } from './products/add-products/add-products.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { SearchProductsComponent } from './products/search-products/search-products.component';
import { ProductComponent } from './products/product/product.component';
import { CheckoutComponent } from './checkout/checkout.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'admin', component: AdminComponent},
  { path: 'user', component: UserComponent},
  { path: 'products', component: AddProductsComponent},
  { path: 'login', component: LoginComponent},
  { path: 'search/:name', component: SearchProductsComponent },
  { path: 'product/:id', component: ProductComponent},
  { path: 'checkout', component: CheckoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }