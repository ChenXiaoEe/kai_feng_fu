package com.example.demo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "trip_refining_business_data_profitshare2025")
public class TripRefiningBusinessDataProfitshare {

    @Id
    private String id;
    private String orderNo;
    private String ticketInfoId;
    private String companyId;
    private String companyName;
    private String goodsCategoryId;

    @Column(name = "goods_category_name")
    private String goodsCategoryName;
    
    private BigDecimal profitshareSalePrice;
    private Integer profitshareQuantity;
    private BigDecimal profitshareTotalPrice;

   
    private BigDecimal discountAmount;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount; 

    
    @Column(name = "use_start_date")
    @Temporal(TemporalType.DATE)
    private Date useStartDate;

    // Getter和Setter方法
    // ...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTicketInfoId() {
        return ticketInfoId;
    }

    public void setTicketInfoId(String ticketInfoId) {
        this.ticketInfoId = ticketInfoId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public BigDecimal getProfitshareSalePrice() {
        return profitshareSalePrice;
    }

    public void setProfitshareSalePrice(BigDecimal profitshareSalePrice) {
        this.profitshareSalePrice = profitshareSalePrice;
    }

    public Integer getProfitshareQuantity() {
        return profitshareQuantity;
    }

    public void setProfitshareQuantity(Integer profitshareQuantity) {
        this.profitshareQuantity = profitshareQuantity;
    }

    public BigDecimal getProfitshareTotalPrice() {
        return profitshareTotalPrice;
    }

    public void setProfitshareTotalPrice(BigDecimal profitshareTotalPrice) {
        this.profitshareTotalPrice = profitshareTotalPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(Date useStartDate) {
        this.useStartDate = useStartDate;
    }

    @Override
    public String toString() {
        return "TripRefiningBusinessDataProfitshare{" +
                "id='" + id + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", ticketInfoId='" + ticketInfoId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", goodsCategoryId='" + goodsCategoryId + '\'' +
                ", goodsCategoryName='" + goodsCategoryName + '\'' +
                ", profitshareSalePrice=" + profitshareSalePrice +
                ", profitshareQuantity=" + profitshareQuantity +
                ", profitshareTotalPrice=" + profitshareTotalPrice +
                ", discountAmount=" + discountAmount +
                ", paymentAmount=" + paymentAmount +
                ", useStartDate=" + useStartDate +
                '}';
    }
}