package com.zwj.excepion;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

}
