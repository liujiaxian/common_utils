package com.jihuiweb.common.qr;

/**
 * Created by Doaim on 2017/5/1.
 */
public class QRParamsException extends RuntimeException {
    private static final long serialVersionUID = 8837582301762730656L;
    public QRParamsException()  {}                //用来创建无参数对象
    public QRParamsException(String message) {    //用来创建指定参数对象
        super(message);                           //调用超类构造器
    }
}