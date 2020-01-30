package com.demo.api;

import com.demo.model.AvgRating;
import com.demo.persistence.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/legostore/api/reports")
public class ReportingController {

    private ReportService reportService;

    public ReportingController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("avgRatingReport")
    public List<AvgRating> avgRatingReport() {
        return this.reportService.getAvgRatingReport();
    }

}
