package com.jihuiweb.common.db;

import com.alibaba.fastjson.annotation.JSONField;
import jodd.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Doaim on 2017/4/20.
 */
public abstract class BaseModel {
    @JSONField(serialize = false)
    private Map<String,Object> data = new ValueKeyHashMap();
    @JSONField(serialize = false)
    private static Map<Class,ModelMetaData> metaDataMap = new ConcurrentHashMap<>();

    public Map<String, Object> getData(){
        return this.data;
    };
    public void setData(Map<String, Object> data){
        this.data = data;
    };

    public void set(String fieldName,Object value){
        this.data.put(fieldName,value);
    };

    public static ModelMetaData getMetaData(Class clazz){
        if(metaDataMap.get(clazz) == null){
            Table table = (Table) clazz.getAnnotation(Table.class);
            ModelMetaData metaData = new ModelMetaData();
            if(table!=null){
                metaData.setTableName(table.name());
            }
            ArrayList<String> idFields = new ArrayList<>();
            List<Class> classList = new ArrayList<>();
            classList.add(clazz);
            Class superClass;
            while ((superClass = clazz.getSuperclass())!=null){
                classList.add(superClass);
                clazz = superClass;
            }
            for(Class clazzz : classList) {
                for (Field field : clazzz.getDeclaredFields()) {
                    Column column = (Column) field.getAnnotation(Column.class);
                    if (column != null) {
                        String columnName = column.name();
                        if (StringUtil.isEmpty(columnName)) {
                            columnName = field.getName();
                        }
                        metaData.setColumnMapping(field.getName(), columnName);
                        if (field.getAnnotation(Id.class) != null) {
                            idFields.add(column.name());
                        }
                    }
                }
            }
            metaData.setId(StringUtil.join(idFields.toArray(),","));
            metaDataMap.put(clazz,metaData);
        }
        return metaDataMap.get(clazz);
    };
}
