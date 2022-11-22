import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { AddProductsComponent } from './products/add-products/add-products.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { SearchProductsComponent } from './products/search-products/search-products.component';
import { ProductComponent } from './products/product/product.component';
import { AdminGuard } from './model/AdminGuard';
import { UserGuard } from 'src/app/model/UserGuard';
import { CheckoutComponent } from './checkout/checkout.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'admin', component: AdminComponent,
  canActivate: [AdminGuard]},
  { path: 'user', component: UserComponent,
  canActivate: [UserGuard]},
  { path: 'cart', component: ShoppingCartComponent,
  canActivate: [UserGuard]},
  { path: 'products', component: AddProductsComponent},
  { path: 'login', component: LoginComponent},
  { path: 'search/:name:types', component: SearchProductsComponent },
  { path: 'product/:id', component: ProductComponent},
  { path: 'search/:name', component: SearchProductsComponent },
  { path: 'checkout', component: CheckoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }