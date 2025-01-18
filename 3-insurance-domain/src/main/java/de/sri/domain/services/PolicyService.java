package de.sri.domain.services;

import de.sri.domain.entities.Policy;

import java.util.ArrayList;
import java.util.List;

public class PolicyService {
	private final List<Policy> policies = new ArrayList<>();

	public void addPolicy(Policy policy) {
		policies.add(policy);
	}

	public List<Policy> getAllPolicies() {
		return policies;
	}
}
