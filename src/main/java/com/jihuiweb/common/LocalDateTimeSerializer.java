package com.jihuiweb.common;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        LocalDateTime value = (LocalDateTime) object;
        if (value == null) {
            out.writeString("");
            return;
        }
        out.write("\""+value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"\"");
    }
}
