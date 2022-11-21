import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../services/statistics.service';
import { StoreStatistics } from '../model/StoreStatistics';
import { UserStatistics } from '../model/UserStatistics';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  public userStats!: UserStatistics[];
  public storeStats!: StoreStatistics;

  constructor(private statsService: StatisticsService) { }

  ngOnInit(): void {
    this.getStats()
  }

  determineHasPurchased(stats: UserStatistics): boolean {
    return stats.purchaseCounter !== 0
  }

  getStats(): void {
    this.statsService.getAllUserStats().subscribe(p => this.userStats = p);
    this.statsService.getStoreStats().subscribe(p => this.storeStats = p);
  }
}
