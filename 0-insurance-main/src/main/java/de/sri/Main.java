package de.sri;

import de.sri.adapters.console.ConsoleAdapter;
import de.sri.application.usecases.ReadCustomerManagementImpl;
import de.sri.application.usecases.WriteCustomerManagementImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.application.usecases.TicketManagementImpl;
import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.ReadCustomerManagement;
import de.sri.domain.usecases.WriteCustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;

public class Main {
    public static void main(String[] args) throws PropertyNotNullException {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        ReadCustomerManagement readCustomerManagement = new ReadCustomerManagementImpl(customerRepository);
        WriteCustomerManagement writeCustomerManagement = new WriteCustomerManagementImpl(customerRepository,
                readCustomerManagement);

        PolicyManagement policyManagement = new PolicyManagementImpl(customerRepository);
        TicketManagement ticketManagement = new TicketManagementImpl(customerRepository, policyManagement);

        ConsoleAdapter consoleAdapter = new ConsoleAdapter(readCustomerManagement, writeCustomerManagement,
                policyManagement, ticketManagement);

        // Start the console application
        System.out.println("Starting Insurance Management System...");
        consoleAdapter.start();
        System.out.println("Insurance Management System closed. Goodbye!");
    }
}
