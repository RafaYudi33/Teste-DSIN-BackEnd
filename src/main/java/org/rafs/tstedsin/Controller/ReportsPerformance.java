package org.rafs.tstedsin.Controller;

import org.rafs.tstedsin.DTOs.WeekPerformance.PerformanceDataResponseDTO;
import org.rafs.tstedsin.Service.WeekPerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reports")
public class ReportsPerformance {

    private final WeekPerformanceService weekPerformanceService;

    public ReportsPerformance(WeekPerformanceService weekPerformanceService) {
        this.weekPerformanceService = weekPerformanceService;
    }

    @GetMapping("/last-week")
    public ResponseEntity<PerformanceDataResponseDTO> weekPerformanceReport(){
        return ResponseEntity.ok().body(weekPerformanceService.weekPerformanceReport());
    }

}
