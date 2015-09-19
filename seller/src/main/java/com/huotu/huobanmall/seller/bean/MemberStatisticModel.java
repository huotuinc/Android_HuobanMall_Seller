package com.huotu.huobanmall.seller.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 */
public class MemberStatisticModel {
    private Long totalMember;
    private Long totalPartner;
    private Long todayMemberAmount;
    private Long weekMemberAmount;
    private Long monthMemberAmount;
    private List<Integer> todayTimes;
    private List<Integer> todayMemberAmounts;
    private List<Date> weekTimes;
    private List<Integer> weekMemberAmounts;
    private List<Date> monthTimes;
    private List<Integer> monthMemberAmounts;
    private Long todayPartnerAmount;
    private Long weekPartnerAmount;
    private Long monthPartnerAmount;
    //private List<Integer> todayPartnerTimes;
    private List<Integer> todayPartnerAmounts;
    //private List<Date> weekPartnerTimes;
    private List<Integer> weekPartnerAmounts;
    //private List<Date> monthPartnerTimes;
    private List<Integer> monthPartnerAmounts;

    public Long getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Long totalMember) {
        this.totalMember = totalMember;
    }

    public Long getTotalPartner() {
        return totalPartner;
    }

    public void setTotalPartner(Long totalPartner) {
        this.totalPartner = totalPartner;
    }

    public void setWeekPartnerAmounts(List<Integer> weekPartnerAmounts) {
        this.weekPartnerAmounts = weekPartnerAmounts;
    }

    public void setTodayMemberAmount(Long todayMemberAmount) {
        this.todayMemberAmount = todayMemberAmount;
    }

    public void setWeekMemberAmount(Long weekMemberAmount) {
        this.weekMemberAmount = weekMemberAmount;
    }

    public void setMonthMemberAmount(Long monthMemberAmount) {
        this.monthMemberAmount = monthMemberAmount;
    }

    public void setTodayTimes(List<Integer> todayTimes) {
        this.todayTimes = todayTimes;
    }

    public void setTodayMemberAmounts(List<Integer> todayMemberAmounts) {
        this.todayMemberAmounts = todayMemberAmounts;
    }

    public void setWeekTimes(List<Date> weekTimes) {
        this.weekTimes = weekTimes;
    }

    public void setWeekMemberAmounts(List<Integer> weekMemberAmounts) {
        this.weekMemberAmounts = weekMemberAmounts;
    }

    public void setMonthTimes(List<Date> monthTimes) {
        this.monthTimes = monthTimes;
    }

    public void setMonthMemberAmounts(List<Integer> monthMemberAmounts) {
        this.monthMemberAmounts = monthMemberAmounts;
    }

    public void setTodayPartnerAmount(Long todayPartnerAmount) {
        this.todayPartnerAmount = todayPartnerAmount;
    }

    public void setWeekPartnerAmount(Long weekPartnerAmount) {
        this.weekPartnerAmount = weekPartnerAmount;
    }

    public void setMonthPartnerAmount(Long monthPartnerAmount) {
        this.monthPartnerAmount = monthPartnerAmount;
    }


    public void setTodayPartnerAmounts(List<Integer> todayPartnerAmounts) {
        this.todayPartnerAmounts = todayPartnerAmounts;
    }


    public void setWeekParnterAmounts(List<Integer> weekParnterAmounts) {
        this.weekPartnerAmounts = weekParnterAmounts;
    }


    public void setMonthPartnerAmounts(List<Integer> monthPartnerAmounts) {
        this.monthPartnerAmounts = monthPartnerAmounts;
    }


    public Long getTodayMemberAmount() {
        return todayMemberAmount;
    }

    public Long getWeekMemberAmount() {
        return weekMemberAmount;
    }

    public Long getMonthMemberAmount() {
        return monthMemberAmount;
    }

    public List<Integer> getTodayTimes() {
        return todayTimes;
    }

    public List<Integer> getTodayMemberAmounts() {
        return todayMemberAmounts;
    }

    public List<Date> getWeekTimes() {
        return weekTimes;
    }

    public List<Integer> getWeekMemberAmounts() {
        return weekMemberAmounts;
    }

    public List<Date> getMonthTimes() {
        return monthTimes;
    }

    public List<Integer> getMonthMemberAmounts() {
        return monthMemberAmounts;
    }

    public Long getTodayPartnerAmount() {
        return todayPartnerAmount;
    }

    public Long getWeekPartnerAmount() {
        return weekPartnerAmount;
    }

    public Long getMonthPartnerAmount() {
        return monthPartnerAmount;
    }


    public List<Integer> getTodayPartnerAmounts() {
        return todayPartnerAmounts;
    }


    public List<Integer> getWeekPartnerAmounts() {
        return weekPartnerAmounts;
    }

    public List<Integer> getMonthPartnerAmounts() {
        return monthPartnerAmounts;
    }
}
