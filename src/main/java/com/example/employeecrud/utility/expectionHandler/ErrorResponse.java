package com.example.employeecrud.utility.expectionHandler;

import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private String timestamp;

    public ErrorResponse(int status, String message, String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }
}
