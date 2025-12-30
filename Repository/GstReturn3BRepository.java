package com.example.RK8.Repository;

import com.example.RK8.Model.Client;
import com.example.RK8.Model.GSTReturn3B;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GstReturn3BRepository extends JpaRepository<GSTReturn3B, Long> {
    Optional<GSTReturn3B> findByClient(Client client);
    void deleteByClient(Client client);
}
