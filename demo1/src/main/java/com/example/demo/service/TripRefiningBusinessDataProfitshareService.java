package com.example.demo.service;

import com.example.demo.pojo.TripRefiningBusinessDataProfitshare;
import com.example.demo.repository.TripRefiningBusinessDataProfitshareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;           // 添加这行
import org.slf4j.LoggerFactory;

import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@Service
public class TripRefiningBusinessDataProfitshareService {

    private static final Logger logger = LoggerFactory.getLogger(TripRefiningBusinessDataProfitshareService.class);

    @Autowired
    private TripRefiningBusinessDataProfitshareRepository repository;
    public List<TripRefiningBusinessDataProfitshare> getByUseStartDate(Date useStartDate) {
        logger.info("查询指定日期数据: {}", useStartDate);
        return repository.findByUseStartDate(useStartDate);
    }

    public Double getTotalPaymentAmountByDateRange(Date startDate, Date endDate) {
        logger.info("查询日期范围内的总支付金额: {} 到 {}", startDate, endDate);
        return repository.findTotalPaymentAmountByDateRange(startDate, endDate);
    }

    public Map<String, Object> getFormattedSummaryByDateRange(Date startDate, Date endDate) {
        try {
            List<Object[]> results = repository.findTotalPaymentAndTicketCountsByDateRange(startDate, endDate);
            
            // 添加详细的调试日志
            logger.info("查询结果数量: {}", results.size());
            for (Object[] result : results) {
                logger.info("原始数据: [金额={}, 类别={}, 数量={}]", result[0], result[1], result[2]);
                // 打印每个字段的类型
                logger.info("数据类型: [金额类型={}, 类别类型={}, 数量类型={}]", 
                    result[0] != null ? result[0].getClass().getName() : "null",
                    result[1] != null ? result[1].getClass().getName() : "null",
                    result[2] != null ? result[2].getClass().getName() : "null");
            }

            if (results == null || results.isEmpty()) {
                return Map.of(
                    "销售金额", 0.0,
                    "活动票", 0,
                    "普通门票", 0,
                    "预售票", 0,
                    "自助票", 0
                );
            }

            Map<String, Object> summary = new HashMap<>();
            double totalPaymentAmount = 0;
            int activityTicketCount = 0;
            int normalTicketCount = 0;
            int presaleTicketCount = 0;
            int selfServiceTicketCount = 0;

            for (Object[] result : results) {
                try {
                    // 处理金额
                    if (result[0] != null) {
                        if (result[0] instanceof BigDecimal) {
                            totalPaymentAmount += ((BigDecimal) result[0]).doubleValue();
                        } else if (result[0] instanceof Double) {
                            totalPaymentAmount += (Double) result[0];
                        }
                    }

                    // 处理类别和数量
                    String category = result[1] != null ? result[1].toString().trim() : "";
                    Number quantity = (Number) result[2];
                    int ticketCount = quantity != null ? quantity.intValue() : 0;

                    // 添加处理过程的日志
                    logger.info("处理数据: 类别='{}', 数量={}, 原始数量={}", category, ticketCount, result[2]);

                    if ("活动票".equalsIgnoreCase(category)) {
                        activityTicketCount += ticketCount;
                    } else if ("普通门票".equalsIgnoreCase(category)) {
                        normalTicketCount += ticketCount;
                    } else if ("预售票".equalsIgnoreCase(category)) {
                        presaleTicketCount += ticketCount;
                    } else if ("自助票".equalsIgnoreCase(category)) {
                        selfServiceTicketCount += ticketCount;
                    } else {
                        logger.warn("未知票类型: '{}'", category);
                    }
                } catch (Exception e) {
                    logger.error("处理数据行时出错: {}", Arrays.toString(result), e);
                }
            }

            summary.put("销售金额", totalPaymentAmount);
            summary.put("活动票", activityTicketCount);
            summary.put("普通门票", normalTicketCount);
            summary.put("预售票", presaleTicketCount);
            summary.put("自助票", selfServiceTicketCount);

            // 添加最终结果日志
            logger.info("最终统计结果: {}", summary);
            return summary;
            
        } catch (Exception e) {
            logger.error("查询处理过程中出错", e);
            throw new RuntimeException("数据处理失败: " + e.getMessage(), e);
        }
    }


//用于获取当月、当周和当日的销售额：
    public Map<String, Object> getSalesByPeriod() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            
            // 设置2025年2月14日的开始时间 (00:00:00)
            calendar.set(2025, Calendar.FEBRUARY, 14, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date dayStart = calendar.getTime();

            // 设置2025年2月15日的开始时间 (00:00:00)作为当日结束
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date nextDay = calendar.getTime();

            // 获取2025年2月份的开始和结束时间
            calendar.set(2025, Calendar.FEBRUARY, 1, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date monthStart = calendar.getTime();
            
            calendar.set(2025, Calendar.MARCH, 1, 0, 0, 0);
            Date monthEnd = calendar.getTime();

            // 获取2025年2月14日所在周的周一和下周一
            calendar.set(2025, Calendar.FEBRUARY, 14);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date weekStart = calendar.getTime();
            
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            Date weekEnd = calendar.getTime();
            
            logger.info("查询时间范围详情:");
            logger.info("当日开始: {}", sdf.format(dayStart));
            logger.info("次日开始: {}", sdf.format(nextDay));
            logger.info("本周开始: {}", sdf.format(weekStart));
            logger.info("下周开始: {}", sdf.format(weekEnd));
            logger.info("本月开始: {}", sdf.format(monthStart));
            logger.info("下月开始: {}", sdf.format(monthEnd));
            
            // 查询销售额
            Double daySales = repository.findDailyTotalPaymentAmount(dayStart, nextDay);
            Double weekSales = repository.findDailyTotalPaymentAmount(weekStart, weekEnd);
            Double monthSales = repository.findDailyTotalPaymentAmount(monthStart, monthEnd);
            
            logger.info("销售额查询结果详情:");
            logger.info("当日销售额: {} (查询范围: {} ~ {})", daySales, sdf.format(dayStart), sdf.format(nextDay));
            logger.info("本周销售额: {} (查询范围: {} ~ {})", weekSales, sdf.format(weekStart), sdf.format(weekEnd));
            logger.info("本月销售额: {} (查询范围: {} ~ {})", monthSales, sdf.format(monthStart), sdf.format(monthEnd));

            Map<String, Object> result = new HashMap<>();
            result.put("当月销售额", monthSales != null ? monthSales : 0.0);
            result.put("本周销售额", weekSales != null ? weekSales : 0.0);
            result.put("当日销售额", daySales != null ? daySales : 0.0);
            
            logger.info("最终返回结果: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("获取销售额数据时出错", e);
            throw new RuntimeException("获取销售额数据失败: " + e.getMessage(), e);
        }
    }
}