package com.little.stockgeek.bean;

/**
 * Created by tusm on 15/8/6.
 */
public class TradeHome {

    String deviceId;  //手机标识

    Double originMoney; // 初始仓位  //存储
    Double sumMoney; // 总资产    //不存储   ＝ restMoney + marketMoney
    Double restMoney; //  可用资金  //存储
    Double marketMoney; //股票市值  //存储



    //总资产 ＝ 股票市值 + 可用资金
    Double earnMoney; //收益
    Double earnRate; //收益率


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getOriginMoney() {
        return originMoney;
    }

    public void setOriginMoney(Double originMoney) {
        this.originMoney = originMoney;
    }


    public Double getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(Double earnMoney) {
        this.earnMoney = earnMoney;
    }

    public Double getEarnRate() {
        return earnRate;
    }

    public void setEarnRate(Double earnRate) {
        this.earnRate = earnRate;
    }

    public Double getRestMoney() {
        return restMoney;
    }

    public void setRestMoney(Double restMoney) {
        this.restMoney = restMoney;
    }

    public Double getMarketMoney() {
        return marketMoney;
    }

    public void setMarketMoney(Double marketMoney) {
        this.marketMoney = marketMoney;
    }


    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

}
