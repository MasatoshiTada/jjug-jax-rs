package com.example.rest.exception.mapper;

import com.example.rest.exception.dto.ExceptionDto;
import java.util.ResourceBundle;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * 実行時例外をキャッチする例外マッパーです。
 * @author tada
 */
@Provider
public class RuntimeExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        exception.printStackTrace(); // 本来はログを取る
        ExceptionDto exceptionDto = createExceptionDto(exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(exceptionDto).build();
    }

    private ExceptionDto createExceptionDto(Exception exception) {
        String exceptionClassName = exception.getClass().getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionMessages");
        String errorType;
        String message;
        if (resourceBundle.containsKey(exceptionClassName)) {
            errorType = resourceBundle.getString(exceptionClassName);
            message = resourceBundle.getString(exceptionClassName + "_detail");
        } else {
            errorType = resourceBundle.getString(RuntimeException.class.getName());
            message = resourceBundle.getString(RuntimeException.class.getName() + "_detail");
        }
        ExceptionDto exceptionDto = new ExceptionDto(errorType, message);
        return exceptionDto;
    }
}
