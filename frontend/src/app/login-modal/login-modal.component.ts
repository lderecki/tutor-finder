import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms'
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog'
import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from '../service/language.service';

@Component({
  selector: 'app-login-modal',
  imports: [
    TranslateModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login-modal.component.html',
  styleUrl: './login-modal.component.scss'
})
export class LoginModalComponent {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<LoginModalComponent>,
    private languageService: LanguageService) {
    this.loginForm = this.formBuilder.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]]
      }
    );

    this.languageService.initLanguage();
  }

  onSubmit(): void {
    if(this.loginForm.valid) {
      console.log('Credentials: ', this.loginForm.value);
    }
  }

  onClose(): void {
    this.matDialogRef.close();
  }
}
