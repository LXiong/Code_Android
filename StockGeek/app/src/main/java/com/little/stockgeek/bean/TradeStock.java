package com.little.stockgeek.bean;

import java.util.Date;

/**
 * Created by tusm on 15/8/6.
 */
public class TradeStock {

    String deviceId;  //手机标识
    String shortCode;   //代码
    String name;  //股票名称


    Double curPrice; //当前价格  （不存储）   后期获取
    Double curVal; //当前市值    （不存储）    后期计算 :  curPrice * haveVol
    Double curCost; //持仓成本    存储: buyVol * 最新价格   或     curCost + buyVol * 最新价格
    Integer haveVol; //拥股数量   //存储
    Double deltaMoney; //盈亏    //后期计算:   curVal - curCost
    Double deltaRate; //盈亏比例   //后期计算:   (curVal - curCost)/curCost



    public TradeStock(String deviceId, String shortCode, String name) {
        this.deviceId = deviceId;
        this.shortCode = shortCode;
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getCurVal() {
        return curVal;
    }

    public void setCurVal(Double curVal) {
        this.curVal = curVal;
    }

    public Integer getHaveVol() {
        return haveVol;
    }

    public void setHaveVol(Integer haveVol) {
        this.haveVol = haveVol;
    }

    public Double getDeltaMoney() {
        return deltaMoney;
    }

    public void setDeltaMoney(Double deltaMoney) {
        this.deltaMoney = deltaMoney;
    }

    public Double getDeltaRate() {
        return deltaRate;
    }

    public void setDeltaRate(Double deltaRate) {
        this.deltaRate = deltaRate;
    }


    public Double getCurCost() {
        return curCost;
    }

    public void setCurCost(Double curCost) {
        this.curCost = curCost;
    }






}
