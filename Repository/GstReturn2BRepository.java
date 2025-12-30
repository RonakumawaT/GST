package com.example.RK8.Repository;

import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn2B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface GstReturn2BRepository extends JpaRepository<GSTReturn2B, Long> {
    List<GSTReturn2B> findByClient(Client client);
    @Query("""
        SELECT COALESCE(SUM(g.itcAmount), 0)
        FROM GSTReturn2B g
        WHERE g.client = :client
    """)
    BigDecimal sumItcByClient(@Param("client") Client client);
}
