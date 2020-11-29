import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HealthCareSharedModule } from 'app/shared/shared.module';
import { HealthCareCoreModule } from 'app/core/core.module';
import { HealthCareAppRoutingModule } from './app-routing.module';
import { HealthCareHomeModule } from './home/home.module';
import { HealthCareEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    HealthCareSharedModule,
    HealthCareCoreModule,
    HealthCareHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HealthCareEntityModule,
    HealthCareAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class HealthCareAppModule {}
