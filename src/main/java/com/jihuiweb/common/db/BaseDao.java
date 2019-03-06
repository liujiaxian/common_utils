package com.jihuiweb.common.db;

import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Doaim on 2017/4/17.
 */
public abstract class BaseDao<T extends BaseModel> {
    static Logger logger = LoggerFactory.getLogger(BaseDao.class);
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    public T findById(Object... ids){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
        ModelMetaData metaData = BaseModel.getMetaData(clazz);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from " + metaData.getTableName() + " where ");
        ArrayList<String> idWhere = new ArrayList<>();
        for(String idName : metaData.getId().split(",")){
            idWhere.add(idName + " = ?");
        }
        sqlBuilder.append(StringUtil.join(idWhere.toArray()," and "));
        return findOne(sqlBuilder.toString(),new BeanPropertyRowMapper<T>(clazz),ids);

    }
    public boolean save(BaseModel model){
        ModelMetaData metaData = model.getMetaData(model.getClass());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into " + metaData.getTableName() + " (");
        ArrayList<String> updateFields = new ArrayList<>();
        ArrayList<Object> updateFieldPlaceholders = new ArrayList<>();
        ArrayList<Object> updateFieldValues = new ArrayList<>();
        model.getData().forEach((key,value)->{
            if(value != null) {
                updateFields.add("`"+key+"`");
                updateFieldValues.add(value);
                updateFieldPlaceholders.add("?");
            }
        });
        sqlBuilder.append(StringUtil.join(updateFields.toArray(),","));
        sqlBuilder.append(")values(");
        sqlBuilder.append(StringUtil.join(updateFieldPlaceholders.toArray(),","));
        sqlBuilder.append(")");
        if(logger.isDebugEnabled()){
            logger.debug(sqlBuilder.toString());
        }
        return jdbcTemplate.update(sqlBuilder.toString(),updateFieldValues.toArray()) > 0;
    };

    /**
     * 批量保存
     * @param models
     * @return
     */
    public void save(List<? extends BaseModel> models){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
        ModelMetaData metaData = BaseModel.getMetaData(clazz);

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into " + metaData.getTableName() + " (");
        ArrayList<String> updateFields = new ArrayList<>();
        ArrayList<Object> updateFieldPlaceholders = new ArrayList<>();

        metaData.getColumnMapping().forEach((key,value)->{
            updateFieldPlaceholders.add("?");
            updateFields.add(metaData.getColumnMapping().get(key));
        });
        List<Object[]> batchSaveValues = new ArrayList<>();
        models.forEach(model -> {
            ArrayList<Object> updateFieldValues = new ArrayList<>();
            updateFields.forEach((field)->{
                updateFieldValues.add(model.getData().get(field));
            });
            batchSaveValues.add(updateFieldValues.toArray());
        });

        sqlBuilder.append(StringUtil.join(updateFields.toArray(),","));
        sqlBuilder.append(")values(");
        sqlBuilder.append(StringUtil.join(updateFieldPlaceholders.toArray(),","));
        sqlBuilder.append(")");
        if(logger.isDebugEnabled()){
            logger.debug(sqlBuilder.toString());
        }
        jdbcTemplate.batchUpdate(sqlBuilder.toString(),batchSaveValues);
    };

    public boolean update(BaseModel model){
        ModelMetaData metaData = model.getMetaData(model.getClass());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update " + metaData.getTableName() + " set ");
        ArrayList<String> updateFields = new ArrayList<>();
        ArrayList<Object> updateFieldValues = new ArrayList<>();
        model.getData().forEach((key,value)->{
            if(!metaData.getId().equals(key) && value != null) {
                updateFields.add(key + " = ?");
                updateFieldValues.add(value);
            }
        });
        sqlBuilder.append(StringUtil.join(updateFields.toArray()," , "));
        sqlBuilder.append(" where ");
        ArrayList<String> idWhere = new ArrayList<>();
        for(String idField : metaData.getId().split(",")){
            idWhere.add(idField + " = ?");
            updateFieldValues.add(model.getData().get(idField));
        }
        sqlBuilder.append(StringUtil.join(idWhere.toArray()," and "));
        if(logger.isDebugEnabled()){
            logger.debug(sqlBuilder.toString());
        }
        return jdbcTemplate.update(sqlBuilder.toString(),updateFieldValues.toArray()) > 0;
    };
    public boolean delete(BaseModel model){
        ModelMetaData metaData = model.getMetaData(model.getClass());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("delete from " + metaData.getTableName() + " where ");
        ArrayList<String> idWhere = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        for(String idName : model.getMetaData(model.getClass()).getId().split(",")){
            idWhere.add(idName + " = ?");
            values.add(model.getData().get(idName));
        }
        sqlBuilder.append(StringUtil.join(idWhere.toArray()," and "));
        return jdbcTemplate.update(sqlBuilder.toString(),values.toArray())>0;
    };
    public boolean deleteById(Object... ids){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
        ModelMetaData metaData = BaseModel.getMetaData(clazz);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("delete from " + metaData.getTableName() + " where ");
        ArrayList<String> idWhere = new ArrayList<>();
        for(String idName : metaData.getId().split(",")){
            idWhere.add(idName + " = ?");
        }
        sqlBuilder.append(StringUtil.join(idWhere.toArray()," and "));
        return jdbcTemplate.update(sqlBuilder.toString(),ids)>0;
    };

    public T findOne(String sql,BeanPropertyRowMapper<T> beanPropertyRowMapper,Object... params){
        T result = null;
        try{
            return jdbcTemplate.queryForObject(sql,beanPropertyRowMapper,params);
        }catch (ConversionFailedException e){
            throw e;
        }catch (TypeMismatchException e){
            throw e;
        }catch (TransientDataAccessResourceException e){
            throw e;
        }catch (Exception e){
            //e.printStackTrace();
            //System.out.println(33);
        }
        return result;
    }
}
