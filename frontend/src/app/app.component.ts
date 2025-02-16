import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RouterOutlet } from '@angular/router';
import { LoginModalComponent } from './login-modal/login-modal.component';
import { TranslateModule } from '@ngx-translate/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { LanguageService } from './service/language.service';
import { MainToolbarComponent } from "./main-toolbar/main-toolbar.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TranslateModule, MatButtonModule, MatIconModule, MainToolbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  providers: [LanguageService]
})
export class AppComponent {
  title = 'frontend';

  constructor(private dialog: MatDialog, private languageService: LanguageService) {
    this.languageService.initLanguage();
  }

  openLoginModal(): void {
    this.dialog.open(LoginModalComponent, {width: '500px'});
  }
}
