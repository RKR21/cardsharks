import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { LogInOutComponent } from './log-in-out/log-in-out.component';
import { AdminInventoryControlsComponent } from './admin-inventory-controls/admin-inventory-controls.component';
import { AdminViewDetailsComponent } from './admin-view-details/admin-view-details.component';
import { CartComponent } from './cart/cart.component';
import { CollectionComponent } from './collection/collection.component';
import { PaymentInfoComponent } from './payment-info/payment-info.component';
import { TradeFormComponent } from './trade-form/trade-form.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: ProductDetailComponent },
  { path: 'trade_detail/:id', component: TradeFormComponent },
  { path: 'admin-detail/:id', component: AdminViewDetailsComponent},
  { path: 'admin-inventory-controls', component: AdminInventoryControlsComponent},
  { path: 'log-in-out', component: LogInOutComponent },
  { path: 'cart', component: CartComponent },
  { path: 'collection', component: CollectionComponent },
  { path: 'payment-info', component: PaymentInfoComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}