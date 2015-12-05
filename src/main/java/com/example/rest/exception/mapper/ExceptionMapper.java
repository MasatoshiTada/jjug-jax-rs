package com.example.rest.exception.mapper;

import com.example.rest.exception.dto.ExceptionDto;
import java.util.ResourceBundle;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * その他の例外をキャッチする例外マッパーです。
 * @author tada
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
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
            errorType = resourceBundle.getString(Exception.class.getName());
            message = resourceBundle.getString(Exception.class.getName() + "_detail");
        }
        ExceptionDto exceptionDto = new ExceptionDto(errorType, message);
        return exceptionDto;
    }
}
