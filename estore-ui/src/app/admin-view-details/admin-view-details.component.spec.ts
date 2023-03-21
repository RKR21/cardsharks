import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminViewDetailsComponent } from './admin-view-details.component';

describe('AdminViewDetailsComponent', () => {
  let component: AdminViewDetailsComponent;
  let fixture: ComponentFixture<AdminViewDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminViewDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminViewDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
