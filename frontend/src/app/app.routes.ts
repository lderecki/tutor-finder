import { Routes } from '@angular/router';
import { StudentViewComponent } from './student-view/student-view.component';
import { TutorViewComponent } from './tutor-view/tutor-view.component';

export const routes: Routes = [
  {
    path: "student-view",
    component: StudentViewComponent
  },
  {
    path: "tutor-view",
    component: TutorViewComponent
  }
];
