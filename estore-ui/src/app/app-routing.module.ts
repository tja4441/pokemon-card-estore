import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { AddProductsComponent } from './add-products/add-products.component';
import { HomeComponent } from './home/home.component';
import { SearchProductsComponent } from './search-products/search-products.component';

const routes: Routes = [
  { path: 'products', component: AddProductsComponent},
  { path: 'admin', component: AdminComponent},
  { path: 'login', component: LoginComponent},
  { path: '', component: HomeComponent},
  { path: 'search', component: SearchProductsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }