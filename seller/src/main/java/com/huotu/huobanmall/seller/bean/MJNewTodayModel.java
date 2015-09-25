package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class MJNewTodayModel extends BaseModel {

    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass {
        private float totalSales;
        private float todaySales;
        private Integer todayPartnerAmount;
        private Integer todayMemberAmount;
        private Integer todayOrderAmount;
        private List<Integer> orderHour;
        private List<Integer> orderAmount;
        private List<Integer> memberHour;
        private List<Integer> memberAmount;
        private List<Integer> partnerHour;
        private List<Integer> partnerAmount;

        public Integer getTodayPartnerAmount() {
            return todayPartnerAmount;
        }

        public Integer getTodayMemberAmount() {
            return todayMemberAmount;
        }

        public Integer getTodayOrderAmount() {
            return todayOrderAmount;
        }

        public void setTodayPartnerAmount(Integer todayPartnerAmount) {
            this.todayPartnerAmount = todayPartnerAmount;
        }

        public void setTodayMemberAmount(Integer todayMemberAmount) {
            this.todayMemberAmount = todayMemberAmount;
        }

        public void setTodayOrderAmount(Integer todayOrderAmount) {
            this.todayOrderAmount = todayOrderAmount;
        }

        public void setTotalSales(float totalSales) {
            this.totalSales = totalSales;
        }

        public void setTodaySales(float todaySales) {
            this.todaySales = todaySales;
        }

        public void setOrderHour(List<Integer> orderHour) {
            this.orderHour = orderHour;
        }

        public void setOrderAmount(List<Integer> orderAmount) {
            this.orderAmount = orderAmount;
        }

        public void setMemberHour(List<Integer> memberHour) {
            this.memberHour = memberHour;
        }

        public void setMemberAmount(List<Integer> memberAmount) {
            this.memberAmount = memberAmount;
        }

        public void setPartnerHour(List<Integer> partnerHour) {
            this.partnerHour = partnerHour;
        }

        public void setPartnerAmount(List<Integer> partnerAmount) {
            this.partnerAmount = partnerAmount;
        }

        public float getTotalSales() {
            return totalSales;
        }

        public float getTodaySales() {
            return todaySales;
        }

        public List<Integer> getOrderHour() {
            return orderHour;
        }

        public List<Integer> getOrderAmount() {
            return orderAmount;
        }

        public List<Integer> getMemberHour() {
            return memberHour;
        }

        public List<Integer> getMemberAmount() {
            return memberAmount;
        }

        public List<Integer> getPartnerHour() {
            return partnerHour;
        }

        public List<Integer> getPartnerAmount() {
            return partnerAmount;
        }
    }
}
