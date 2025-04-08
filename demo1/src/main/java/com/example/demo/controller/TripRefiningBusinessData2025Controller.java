package com.example.demo.controller;

import com.example.demo.service.TripRefiningBusinessData2025Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/business-data")
@CrossOrigin(origins = "*")
public class TripRefiningBusinessData2025Controller {

    @Autowired
    private TripRefiningBusinessData2025Service service;

    @GetMapping("/summary")
    public Map<String, Object> getSalesSummary(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return service.getSalesSummaryByDateRange(startDate, endDate);
    }
} 