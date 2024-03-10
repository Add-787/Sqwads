import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { HeaderComponent } from './header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field'
import { FooterComponent } from './footer/footer.component';
import { NewLayoutComponent } from './new-layout/new-layout.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon'



@NgModule({
  declarations: [
    LayoutComponent,
    HeaderComponent,
    FooterComponent,
    NewLayoutComponent,
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatListModule,
    MatFormFieldModule,
    RouterModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule
  ],
  exports: [
    LayoutComponent,
    NewLayoutComponent
  ]
})
export class CoreModule { }
