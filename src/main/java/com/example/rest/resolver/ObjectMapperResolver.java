package com.example.rest.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * JSONパーサーJacksonを有効化するためのクラス。
 * @author tada
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

    @Override
    public ObjectMapper getContext(Class<?> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 出力するJSONを整形する(Pretty Print)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
    
}
