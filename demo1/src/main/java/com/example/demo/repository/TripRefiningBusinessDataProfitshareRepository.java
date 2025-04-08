package com.example.demo.repository;
import com.example.demo.pojo.TripRefiningBusinessDataProfitshare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TripRefiningBusinessDataProfitshareRepository extends JpaRepository<TripRefiningBusinessDataProfitshare, String> {
    // 自定义查询方法
    List<TripRefiningBusinessDataProfitshare> findByCompanyId(String companyId);

    // 根据useStartDate查询
    List<TripRefiningBusinessDataProfitshare> findByUseStartDate(Date useStartDate);

    // 根据时间范围查询总金额（用于周和月的查询）
    @Query("SELECT SUM(t.paymentAmount) FROM TripRefiningBusinessDataProfitshare t " +
           "WHERE t.useStartDate BETWEEN :startDate AND :endDate")
    Double findTotalPaymentAmountByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 精确查询当日销售额
    @Query(value = "SELECT SUM(payment_amount) FROM trip_refining_business_data_profitshare2025 " +
           "WHERE use_start_date >= :startDate AND use_start_date < :nextDay", nativeQuery = true)
    Double findDailyTotalPaymentAmount(@Param("startDate") Date startDate, @Param("nextDay") Date nextDay);

    // 根据时间范围查询总金额和票数
    @Query("SELECT " +
           "SUM(t.paymentAmount) as totalAmount, " +
           "t.goodsCategoryName as category, " +
           "SUM(t.profitshareQuantity) as totalQuantity " +
           "FROM TripRefiningBusinessDataProfitshare t " +
           "WHERE t.useStartDate BETWEEN :startDate AND :endDate " +
           "GROUP BY t.goodsCategoryName")
    List<Object[]> findTotalPaymentAndTicketCountsByDateRange(
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);
}


