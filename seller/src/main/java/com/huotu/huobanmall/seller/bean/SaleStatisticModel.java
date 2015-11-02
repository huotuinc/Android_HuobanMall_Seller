package com.huotu.huobanmall.seller.bean;

import java.nio.channels.FileLock;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public class SaleStatisticModel {
    private Double totalAmount;
    private Double todayAmount;
    private Double weekAmount;
    private Double monthAmount;
    private List<Integer> todayTimes;
    private List<Double> todayAmounts;
    private List<Date> weekTimes;
    private List<Double> weekAmounts;
    private List<Date> monthTimes;
    private List<Double> monthAmounts;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Double todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Double getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Double weekAmount) {
        this.weekAmount = weekAmount;
    }

    public Double getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
    }

    public List<Integer> getTodayTimes() {
        return todayTimes;
    }

    public void setTodayTimes(List<Integer> todayTimes) {
        this.todayTimes = todayTimes;
    }

    public List<Double> getTodayAmounts() {
        return todayAmounts;
    }

    public void setTodayAmounts(List<Double> todayAmounts) {
        this.todayAmounts = todayAmounts;
    }

    public List<Date> getWeekTimes() {
        return weekTimes;
    }

    public void setWeekTimes(List<Date> weekTimes) {
        this.weekTimes = weekTimes;
    }

    public List<Double> getWeekAmounts() {
        return weekAmounts;
    }

    public void setWeekAmounts(List<Double> weekAmounts) {
        this.weekAmounts = weekAmounts;
    }

    public List<Date> getMonthTimes() {
        return monthTimes;
    }

    public void setMonthTimes(List<Date> monthTimes) {
        this.monthTimes = monthTimes;
    }

    public List<Double> getMonthAmounts() {
        return monthAmounts;
    }

    public void setMonthAmounts(List<Double> monthAmounts) {
        this.monthAmounts = monthAmounts;
    }

}
