import { LanguageService } from './../service/language.service';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatMenuModule } from '@angular/material/menu';
import { TranslateModule } from '@ngx-translate/core';
import { LoginModalComponent } from '../login-modal/login-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-main-toolbar',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule, TranslateModule, RouterLink, RouterLinkActive],
  templateUrl: './main-toolbar.component.html',
  styleUrl: './main-toolbar.component.scss'
})
export class MainToolbarComponent {

  constructor(private dialog: MatDialog, private languageService: LanguageService) {
    this.languageService.initLanguage();
  }

  openLoginModal(): void {
      this.dialog.open(LoginModalComponent, {width: '500px'});
    }

}
