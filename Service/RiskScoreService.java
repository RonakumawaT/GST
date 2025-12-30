package com.example.RK8.Service;

import com.example.RK8.Model.Client;
import org.springframework.stereotype.Service;

@Service
public class RiskScoreService {

    public void calculateRiskForClient(Client client) {
        // FOR NOW: dummy logic
        // Later: 2B vs 3B vs ITC mismatch
        System.out.println("Calculating risk for: " + client.getGstin());
    }
}

