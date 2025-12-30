package com.example.RK8.DTO;


import java.util.List;

public class UploadResult {

    private int successCount;
    private int failureCount;
    private List<UploadError> errors;

    public UploadResult(int successCount, List<UploadError> errors) {
        this.successCount = successCount;
        this.errors = errors;
        this.failureCount = errors.size();
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public List<UploadError> getErrors() {
        return errors;
    }
}
