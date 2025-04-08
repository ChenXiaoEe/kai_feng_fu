package com.example.demo.controller;

import com.example.demo.pojo.TripRefiningBusinessDataProfitshare;
import com.example.demo.service.TripRefiningBusinessDataProfitshareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TripRefiningBusinessDataProfitshareController {
    private static final Logger logger = LoggerFactory.getLogger(TripRefiningBusinessDataProfitshareController.class);
    
    @Autowired
    private TripRefiningBusinessDataProfitshareService service;

    @GetMapping("/profitshare/by-date")
    public List<TripRefiningBusinessDataProfitshare> getByUseStartDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date useStartDate) {
        logger.info("查询指定日期数据: {}", useStartDate);
        return service.getByUseStartDate(useStartDate);
    }

    @GetMapping("/profitshare/total-payment")
    public Double getTotalPaymentAmountByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return service.getTotalPaymentAmountByDateRange(startDate, endDate);
    }

    @GetMapping("/profitshare/summary")
    public Map<String, Object> getFormattedSummaryByDateRange(
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("开始日期和结束日期不能为空");
            }
            
            if (startDate.after(endDate)) {
                throw new IllegalArgumentException("开始日期不能晚于结束日期");
            }

            return service.getFormattedSummaryByDateRange(startDate, endDate);
            
        } catch (Exception e) {
            throw new RuntimeException("查询失败: " + e.getMessage());
        }
    }
//日月周销售额
    @GetMapping("/profitshare/sales-by-period")
    public Map<String, Object> getSalesByPeriod() {
        try {
            return service.getSalesByPeriod();
        } catch (Exception e) {
            throw new RuntimeException("查询失败: " + e.getMessage());
        }
    }
}