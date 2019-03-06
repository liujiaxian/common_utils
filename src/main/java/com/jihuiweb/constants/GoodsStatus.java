package com.jihuiweb.constants;

/**
 * Created by Doaim on 2017/6/23.
 * 订单状态
 */
public class GoodsStatus {
    public static int WAITING_PAY = 0 ;	//未支付
    public static int ALREADY_PAY = 2 ;	//已支付
    public static int WAITING_DISPATCH = 4 ;	//等待分配
    public static int WAITING_DISPATCHED = 6 ;	//分配完成
    public static int WAITING_HANDLE = 8 ;	//正在处理
    public static int WAITING_REVIEW = 10 ;	//处理完成，等待处理状态确认
    public static int HANDLE_CONFIRM_FAILD = 12; 	//处理状态确认失败，等待重新确认
    public static int HANDLE_CONFIRMED = 14; 	//处理状态已确认
    public static int HANDLE_SUCCESS = 16; 	//成功处理
    public static int HANDLE_FAILED = 17; 	//处理失败
    public static int REFUNDING = 18; 	//正在退款
    public static int REFUNDED = 20; 	//退款完成
    public static int REFUND_FAILED = 22; 	//退款失败
}