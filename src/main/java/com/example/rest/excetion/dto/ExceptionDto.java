package com.example.rest.excetion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author tada
 */
@JsonPropertyOrder({"error_type", "messages"})
public class ExceptionDto {

    @JsonProperty("error_type")
    private String errorType;
    
    private String[] messages;

    public ExceptionDto(String errorType, String... messages) {
        this.errorType = errorType;
        this.messages = messages;
    }

    /**
     * @return the errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the messages
     */
    public String[] getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String[] messages) {
        this.messages = messages;
    }
    
}
