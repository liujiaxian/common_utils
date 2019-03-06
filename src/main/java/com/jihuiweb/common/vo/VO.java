package com.jihuiweb.common.vo;

import com.jihuiweb.common.db.BaseModel;

import java.util.HashMap;

public class VO extends HashMap<String,Object> {
    public VO valueOf(Object o){return null;};
    public static VO valueOf(BaseModel model,String[] fields){
        VO vo = new VO();
        for(int i = 0;i<fields.length;i++){
            vo.put(fields[i],model.getData().get(fields[i]));
        }
        return vo;
    }
}
