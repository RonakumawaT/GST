package com.example.RK8.Repository;

import com.example.RK8.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByGstin(String gstin);
    List<Client> findByGstinIn(Set<String> gstins);

}
