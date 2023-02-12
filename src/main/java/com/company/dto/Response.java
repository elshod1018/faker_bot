package com.company.dto;

import lombok.Getter;

@Getter
public class Response<T> {
    private T body;
    private final boolean success;
    private String friendlyErrorMessage;
    private String developerErrorMessage;
    private Integer total;

    public Response(T body, Integer total) {
        this.body = body;
        this.total = total;
        this.success = true;
    }

    public Response(T body) {
        this(body, null);
    }


    public Response(String friendlyErrorMessage, String developerErrorMessage) {
        this.friendlyErrorMessage = friendlyErrorMessage;
        this.developerErrorMessage = developerErrorMessage;
        this.success = false;
    }

}
