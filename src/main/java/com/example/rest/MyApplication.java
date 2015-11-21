package com.example.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.CommonProperties;

/**
 * JAX-RSを有効化するためのクラス。
 * @author tada
 */
// TODO: 演習1-1. Applicationクラスを継承する。アプリケーションパスは"api"を指定する。

public class MyApplication    {

//    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        // JSONパーサーMOXyの無効化
        properties.put(CommonProperties.MOXY_JSON_FEATURE_DISABLE, Boolean.TRUE);
        return properties;
    }
    
}
