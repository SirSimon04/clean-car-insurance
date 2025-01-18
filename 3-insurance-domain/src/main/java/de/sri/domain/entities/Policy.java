package de.sri.domain.entities;

public class Policy {
	private String policyNumber;
	private String policyHolderName;

	public Policy(String policyNumber, String policyHolderName) {
		this.policyNumber = policyNumber;
		this.policyHolderName = policyHolderName;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}
}
