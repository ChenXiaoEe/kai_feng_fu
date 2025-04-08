package com.example.demo.repository;

import com.example.demo.pojo.TripRefiningBusinessData2025;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface TripRefiningBusinessData2025Repository extends JpaRepository<TripRefiningBusinessData2025, String> {
    
    // 查询指定日期范围内的总销售额
    @Query(value = "SELECT COALESCE(SUM(payment_amount), 0) FROM `trip_refining_business_data-2025` " +
           "WHERE order_time >= :startDate AND order_time < :endDate", nativeQuery = true)
    BigDecimal findTotalPaymentAmountByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 查询线上线下购票数量
    @Query(value = "SELECT is_online, COUNT(*) as count FROM `trip_refining_business_data-2025` " +
           "WHERE order_time >= :startDate AND order_time < :endDate " +
           "GROUP BY is_online", nativeQuery = true)
    List<Object[]> findTicketCountByOnlineStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 查询各种票名称的数量
    @Query(value = "SELECT ticket_name, COUNT(*) as count FROM `trip_refining_business_data-2025` " +
           "WHERE order_time >= :startDate AND order_time < :endDate " +
           "GROUP BY ticket_name", nativeQuery = true)
    List<Object[]> findTicketCountByTicketName(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 查询现金和非现金购票数量
    @Query(value = "SELECT is_cash, COUNT(*) as count FROM `trip_refining_business_data-2025` " +
           "WHERE order_time >= :startDate AND order_time < :endDate " +
           "GROUP BY is_cash", nativeQuery = true)
    List<Object[]> findTicketCountByCashStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
} 