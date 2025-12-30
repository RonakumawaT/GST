package com.example.RK8.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "gst_return_3b")
@Data
public class GSTReturn3B {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    @Column(name = "itc_claimed")
    private BigDecimal itcClaimed;

}

