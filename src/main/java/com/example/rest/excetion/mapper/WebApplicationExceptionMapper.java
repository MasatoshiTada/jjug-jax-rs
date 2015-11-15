package com.example.rest.excetion.mapper;

import com.example.rest.excetion.dto.ExceptionDto;
import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * WebApplicationException及びそのサブクラスをキャッチする例外マッパーです。
 * @author tada
 */
@Provider
public class WebApplicationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        exception.printStackTrace(); // 本来はログを取る
        ExceptionDto exceptionDto = createExceptionDto(exception);
        return Response.status(exception.getResponse().getStatusInfo())
                .entity(exceptionDto).build();
    }

    private ExceptionDto createExceptionDto(WebApplicationException exception) {
        String exceptionClassName = exception.getClass().getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionMessages");
        String errorType;
        if (resourceBundle.containsKey(exceptionClassName)) {
            errorType = resourceBundle.getString(exceptionClassName);
        } else {
            errorType = resourceBundle.getString(WebApplicationException.class.getName());
        }
        String message = exception.getMessage();
        ExceptionDto exceptionDto = new ExceptionDto(errorType, message);
        return exceptionDto;
    }
}
