package com.huotu.huobanmall.seller.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class BillStatisticModel {
    private Long totalAmount;
    private Long todayAmount;
    private Long weekAmount;
    private Long monthAmount;
    private List<Integer> todayTimes;
    private List<Integer> todayAmounts;
    private List<Date> weekTimes;
    private List<Integer> weekAmounts;
    private List<Date> monthTimes;
    private List<Integer> monthAmounts;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setTodayAmount(Long todayAmount) {
        this.todayAmount = todayAmount;
    }

    public void setWeekAmount(Long weekAmount) {
        this.weekAmount = weekAmount;
    }

    public void setMonthAmount(Long monthAmount) {
        this.monthAmount = monthAmount;
    }

    public void setTodayTimes(List<Integer> todayTimes) {
        this.todayTimes = todayTimes;
    }

    public void setTodayAmounts(List<Integer> todayAmounts) {
        this.todayAmounts = todayAmounts;
    }

    public void setWeekTimes(List<Date> weekTimes) {
        this.weekTimes = weekTimes;
    }

    public void setWeekAmounts(List<Integer> weekAmounts) {
        this.weekAmounts = weekAmounts;
    }

    public void setMonthTimes(List<Date> monthTimes) {
        this.monthTimes = monthTimes;
    }

    public void setMonthAmounts(List<Integer> monthAmounts) {
        this.monthAmounts = monthAmounts;
    }



    public Long getTodayAmount() {
        return todayAmount;
    }

    public Long getWeekAmount() {
        return weekAmount;
    }

    public Long getMonthAmount() {
        return monthAmount;
    }

    public List<Integer> getTodayTimes() {
        return todayTimes;
    }

    public List<Integer> getTodayAmounts() {
        return todayAmounts;
    }

    public List<Date> getWeekTimes() {
        return weekTimes;
    }

    public List<Integer> getWeekAmounts() {
        return weekAmounts;
    }

    public List<Date> getMonthTimes() {
        return monthTimes;
    }

    public List<Integer> getMonthAmounts() {
        return monthAmounts;
    }



}
