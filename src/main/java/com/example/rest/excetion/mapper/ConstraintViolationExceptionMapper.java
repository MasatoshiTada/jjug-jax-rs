package com.example.rest.excetion.mapper;

import com.example.rest.excetion.dto.ExceptionDto;
import java.util.ResourceBundle;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * バリデーション例外をキャッチする例外マッパーです。
 * @author tada
 */
@Provider
public class ConstraintViolationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        exception.printStackTrace();
        String exceptionClassName = exception.getClass().getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionMessages");
        String errorType = resourceBundle.getString(exceptionClassName);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String[] messages = violations.stream()
                .map(violation -> violation.getMessage())
                .toArray(String[]::new);
        ExceptionDto exceptionDto = new ExceptionDto(errorType, messages);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exceptionDto).build();
    }
    
}
