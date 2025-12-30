package com.example.RK8;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Gst2BUploadRow {

    private String gstin;
    private String invoiceNo;
    private String supplierGstin;
    private BigDecimal itcAmount;

    // getters & setters
}

