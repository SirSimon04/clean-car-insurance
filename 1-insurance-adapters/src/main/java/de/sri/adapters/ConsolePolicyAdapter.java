package de.sri.adapters;

import de.sri.application.usecases.CreatePolicyUseCase;
import de.sri.domain.services.PolicyService;

import java.util.Scanner;

public class ConsolePolicyAdapter {
    public static void main(String[] args) {
        PolicyService policyService = new PolicyService();
        CreatePolicyUseCase createPolicyUseCase = new CreatePolicyUseCase(policyService);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Policy Number:");
        String policyNumber = scanner.nextLine();
        System.out.println("Enter Policy Holder Name:");
        String policyHolderName = scanner.nextLine();

        createPolicyUseCase.execute(policyNumber, policyHolderName);
        System.out.println("Policy Created Successfully!");
    }
}
