import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../statistics.service';
import { UserStatistics } from '../UserStatistics';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  public userStats: UserStatistics[];

  constructor(statisticsService: StatisticsService) { }

  ngOnInit(): void {

  }

}
