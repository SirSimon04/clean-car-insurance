package de.sri;

import de.sri.adapters.console.ConsoleAdapter;
import de.sri.application.usecases.CustomerManagementImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.application.usecases.TicketManagementImpl;
import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        CustomerManagement customerManagement = new CustomerManagementImpl(customerRepository);
        PolicyManagement policyManagement = new PolicyManagementImpl(customerManagement);
        TicketManagement ticketManagement = new TicketManagementImpl(customerManagement, policyManagement);

        ConsoleAdapter consoleAdapter = new ConsoleAdapter(customerManagement, policyManagement, ticketManagement);

        // Start the console application
        System.out.println("Starting Insurance Management System...");
        consoleAdapter.start();
        System.out.println("Insurance Management System closed. Goodbye!");
    }
}
