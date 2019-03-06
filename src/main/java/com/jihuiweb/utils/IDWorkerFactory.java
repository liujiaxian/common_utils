package com.jihuiweb.utils;

/**
 * Created by Doaim on 2017/4/17.
 */
public class IDWorkerFactory{
    private static IDWorker idWorker = null;
    public static IDWorker getInstance(){
        if(idWorker == null){
            idWorker = new SnowflakeIdWorker(0l,0l);
        }
        return idWorker;
    }
}
