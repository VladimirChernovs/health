import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HealthSharedModule } from 'app/shared/shared.module';
import { HealthCoreModule } from 'app/core/core.module';
import { HealthAppRoutingModule } from './app-routing.module';
import { HealthHomeModule } from './home/home.module';
import { HealthEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    HealthSharedModule,
    HealthCoreModule,
    HealthHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HealthEntityModule,
    HealthAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class HealthAppModule {}
