package com.huotu.huobanmall.seller.bean;

import java.nio.channels.FileLock;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public class SaleStatisticModel {
    private Float totalAmount;
    private Float todayAmount;
    private Float weekAmount;
    private Float monthAmount;
    private List<Integer> todayTimes;
    private List<Float> todayAmounts;
    private List<Date> weekTimes;
    private List<Float> weekAmounts;
    private List<Date> monthTimes;
    private List<Float> monthAmounts;

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Float todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Float getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Float weekAmount) {
        this.weekAmount = weekAmount;
    }

    public Float getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Float monthAmount) {
        this.monthAmount = monthAmount;
    }

    public List<Integer> getTodayTimes() {
        return todayTimes;
    }

    public void setTodayTimes(List<Integer> todayTimes) {
        this.todayTimes = todayTimes;
    }

    public List<Float> getTodayAmounts() {
        return todayAmounts;
    }

    public void setTodayAmounts(List<Float> todayAmounts) {
        this.todayAmounts = todayAmounts;
    }

    public List<Date> getWeekTimes() {
        return weekTimes;
    }

    public void setWeekTimes(List<Date> weekTimes) {
        this.weekTimes = weekTimes;
    }

    public List<Float> getWeekAmounts() {
        return weekAmounts;
    }

    public void setWeekAmounts(List<Float> weekAmounts) {
        this.weekAmounts = weekAmounts;
    }

    public List<Date> getMonthTimes() {
        return monthTimes;
    }

    public void setMonthTimes(List<Date> monthTimes) {
        this.monthTimes = monthTimes;
    }

    public List<Float> getMonthAmounts() {
        return monthAmounts;
    }

    public void setMonthAmounts(List<Float> monthAmounts) {
        this.monthAmounts = monthAmounts;
    }

}
