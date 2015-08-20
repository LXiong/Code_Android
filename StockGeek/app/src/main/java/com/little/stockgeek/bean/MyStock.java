package com.little.stockgeek.bean;

/**
 * Created by tusm on 15/8/4.
 */
public class MyStock {


    String shortCode;   //代码
    String fullCode;  //sz  sh代码
    String name;  //股票名称
    String stockRange; //涨跌幅
    String stockPrice; //现价

    public MyStock(){}

    public MyStock(String code, String fullCode, String name) {
        this.shortCode = code;
        this.fullCode = fullCode;
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String code) {
        this.shortCode = code;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockRange() {
        return stockRange;
    }

    public void setStockRange(String stockRange) {
        this.stockRange = stockRange;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }
}
