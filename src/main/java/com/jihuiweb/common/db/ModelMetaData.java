package com.jihuiweb.common.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Doaim on 2017/4/20.
 * Model 元数据
 */
public class ModelMetaData {
    private String tableName;
    //ID
    private String id;
    //属性与数据库字段映射
    private Map<String,String> columnMapping = new HashMap<>();
    private Map<String,String> columnFieldMapping = new HashMap<>();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getColumnMapping() {
        return columnMapping;
    }
    public Map<String, String> getColumnFieldMapping() {
        return columnFieldMapping;
    }

    public void setColumnMapping(String fieldName,String columnName){
        columnFieldMapping.put(columnName,fieldName);
        columnMapping.put(fieldName,columnName);
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
