import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INurse } from 'app/shared/model/nurse.model';

@Component({
  selector: 'jhi-nurse-detail',
  templateUrl: './nurse-detail.component.html',
})
export class NurseDetailComponent implements OnInit {
  nurse: INurse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nurse }) => (this.nurse = nurse));
  }

  previousState(): void {
    window.history.back();
  }
}
