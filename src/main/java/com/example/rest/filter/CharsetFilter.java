package com.example.rest.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * レスポンスヘッダーに文字コードを設定するフィルターです。
 * @author tada
 */
@Provider
public class CharsetFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        MediaType mediaType = (MediaType) headers.getFirst("Content-Type");
        if (mediaType != null) {
            headers.putSingle("Content-Type", mediaType.withCharset("UTF-8"));
        }
    }
    
}
