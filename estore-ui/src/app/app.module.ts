import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { MessagesComponent } from './messages/messages.component';

import { ProductSearchComponent } from './product-search/product-search.component';
import { LogInOutComponent } from './log-in-out/log-in-out.component';
import { AdminInventoryControlsComponent } from './admin-inventory-controls/admin-inventory-controls.component';
import { AdminViewDetailsComponent } from './admin-view-details/admin-view-details.component';
import { CartComponent } from './cart/cart.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    MessagesComponent,
    ProductDetailComponent,
    ProductSearchComponent,
    AdminInventoryControlsComponent,
    AdminViewDetailsComponent,
    LogInOutComponent,
    CartComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }