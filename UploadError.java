package com.example.RK8.DTO;

public class UploadError {

    private int rowNumber;
    private String message;

    public UploadError(int rowNumber, String message) {
        this.rowNumber = rowNumber;
        this.message = message;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public String getMessage() {
        return message;
    }
}
