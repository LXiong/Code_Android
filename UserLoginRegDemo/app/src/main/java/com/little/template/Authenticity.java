package com.little.template;

/**
 * Created by tusm on 15/9/4.
 */
public interface Authenticity {
    boolean isNeedNet();
    boolean isNeedLogin();
    boolean isNeedWifi();

    void checkAuthenticity();
}
