import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminInventoryControlsComponent } from './admin-inventory-controls.component';

describe('AdminInventoryControlsComponent', () => {
  let component: AdminInventoryControlsComponent;
  let fixture: ComponentFixture<AdminInventoryControlsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminInventoryControlsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminInventoryControlsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
