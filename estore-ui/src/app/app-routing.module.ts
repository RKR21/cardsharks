import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { LogInOutComponent } from './log-in-out/log-in-out.component';
import { AdminInventoryControlsComponent } from './admin-inventory-controls/admin-inventory-controls.component';
import { AdminViewDetailsComponent } from './admin-view-details/admin-view-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: HeroDetailComponent },
  { path: 'admin-detail/:id', component: AdminViewDetailsComponent},
  { path: 'heroes', component: HeroesComponent },
  { path: 'admin-inventory-controls', component: AdminInventoryControlsComponent},
  { path: 'log-in-out', component: LogInOutComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}