package com.little.stockgeek.bean;

/**
 * Created by tusm on 15/8/6.
 */
public class SimpleStock {
    String shortCode;   //代码
    String fullCode;  //sz  sh代码
    String name;  //股票名称


    public SimpleStock(String shortCode, String fullCode, String name) {
        this.shortCode = shortCode;
        this.fullCode = fullCode;
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
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
}
