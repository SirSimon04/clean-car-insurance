package de.sri.application.usecases;

import de.sri.domain.entities.Policy;
import de.sri.domain.services.PolicyService;

public class CreatePolicyUseCase {
    private final PolicyService policyService;

    public CreatePolicyUseCase(PolicyService policyService) {
        this.policyService = policyService;
    }

    public void execute(String policyNumber, String policyHolderName) {
        Policy policy = new Policy(policyNumber, policyHolderName);
        policyService.addPolicy(policy);
    }
}

