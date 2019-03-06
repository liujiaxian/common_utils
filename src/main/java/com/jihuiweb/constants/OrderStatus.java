package com.jihuiweb.constants;

/**
 * Created by Doaim on 2017/6/23.
 * 订单状态
 */
public class OrderStatus {
    public static int WATING_PAY = 0 ;//	未支付
    public static int ALREADY_PAY = 1 ;//	已支付
    public static int WATING_DISPATCH = 2 ;//	等待分配
    public static int FROZEN = 3 ;//	订单已超时
    public static int WAITING_HANDLE = 4 ;//	正在处理
    public static int HANDLE_COMPLETE = 6 ;//	处理完成，等待处理状态确认
    public static int HANDLE_CONFIRM_FAILD = 8 ;//	处理状态确认失败，等待重新确认
    public static int HANDLE_CONFIRM = 10;// 	处理状态已确认
    public static int ORDER_COMPLETE = 12;// 	订单完成
    public static int REFUNDING = 14;// 	正在退款
    public static int REFUNDED = 16;// 	退款完成
    public static int WATING_UPLOAD_DOC = 18;// 	等待上传证件
}
