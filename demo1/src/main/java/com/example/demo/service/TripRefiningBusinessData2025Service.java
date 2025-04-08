package com.example.demo.service;

import com.example.demo.repository.TripRefiningBusinessData2025Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TripRefiningBusinessData2025Service {
    private static final Logger logger = LoggerFactory.getLogger(TripRefiningBusinessData2025Service.class);

    @Autowired
    private TripRefiningBusinessData2025Repository repository;

    public Map<String, Object> getSalesSummaryByDateRange(Date startDate, Date endDate) {
        try {
            // 查询总销售额
            BigDecimal totalPayment = repository.findTotalPaymentAmountByDateRange(startDate, endDate);
            
            // 查询线上线下购票数量
            List<Object[]> onlineCounts = repository.findTicketCountByOnlineStatus(startDate, endDate);
            Map<String, Long> onlineStats = new HashMap<>();
            for (Object[] result : onlineCounts) {
                String key = String.valueOf(result[0]); // 将Character转换为String
                Long value = ((Number) result[1]).longValue();
                onlineStats.put(key, value);
            }
            
            // 查询各种票名称的数量
            List<Object[]> ticketCounts = repository.findTicketCountByTicketName(startDate, endDate);
            Map<String, Long> ticketStats = new HashMap<>();
            for (Object[] result : ticketCounts) {
                String key = String.valueOf(result[0]);
                Long value = ((Number) result[1]).longValue();
                ticketStats.put(key, value);
            }
            
            // 查询现金和非现金购票数量
            List<Object[]> cashCounts = repository.findTicketCountByCashStatus(startDate, endDate);
            Map<String, Long> paymentStats = new HashMap<>();
            for (Object[] result : cashCounts) {
                String key = String.valueOf(result[0]).equals("T") ? "现金支付" : "银联支付";
                Long value = ((Number) result[1]).longValue();
                paymentStats.put(key, value);
            }

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("totalPayment", totalPayment != null ? totalPayment : BigDecimal.ZERO);
            result.put("onlineStats", onlineStats);
            result.put("ticketStats", ticketStats);
            result.put("paymentStats", paymentStats);

            logger.info("销售统计结果: {}", result);
            return result;
            
        } catch (Exception e) {
            logger.error("获取销售统计时出错", e);
            throw new RuntimeException("获取销售统计失败: " + e.getMessage());
        }
    }
} 