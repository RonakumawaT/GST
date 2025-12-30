package com.example.RK8.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "gst_return_2b")
@Data
public class GSTReturn2B {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many invoices → one client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "supplier_gstin")
    private String supplierGstin;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "itc_amount")
    private BigDecimal itcAmount;


}

