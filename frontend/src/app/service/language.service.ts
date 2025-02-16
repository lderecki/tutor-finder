import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  private supportedLanguages = ['en', 'pl'];

  constructor(private translate: TranslateService) {}

  initLanguage() {
    const browserLang = navigator.language || navigator.languages[0];
    const appLanguage = browserLang.split('-')[0];

    if (this.supportedLanguages.includes(appLanguage)) {
      this.translate.use(appLanguage);
    } else {
      this.translate.use('en');
    }
  }

  changeLanguage(language: string) {
    this.translate.use(language);
  }
}
