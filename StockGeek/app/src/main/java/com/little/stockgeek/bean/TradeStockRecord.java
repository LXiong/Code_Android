package com.little.stockgeek.bean;

import java.util.Date;

/**
 * Created by tusm on 15/8/6.
 */
public class TradeStockRecord {

    String deviceId;  //手机标识
    String shortCode;   //代码
    String name;  //股票名称
    String tradeType; //交易类型

    Date tradeDate; //日期
    Double tradePrice; //交易价格
    Integer tradeVal; //交易类型

    public TradeStockRecord(String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal) {
        this.deviceId = deviceId;
        this.shortCode = shortCode;
        this.name = name;
        this.tradeType = tradeType;
        this.tradeDate = tradeDate;
        this.tradePrice = tradePrice;
        this.tradeVal = tradeVal;
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public Integer getTradeVal() {
        return tradeVal;
    }

    public void setTradeVal(Integer tradeVal) {
        this.tradeVal = tradeVal;
    }






}
