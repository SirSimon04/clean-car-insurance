package de.sri;

import de.sri.adapters.console.ConsoleAdapter;
import de.sri.application.usecases.CustomerManagementImpl;
import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.CustomerManagement;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        CustomerManagement customerService = new CustomerManagementImpl(customerRepository);

        ConsoleAdapter consoleAdapter = new ConsoleAdapter(customerService);

        // Start the console application
        System.out.println("Starting Insurance Management System...");
        consoleAdapter.start();
        System.out.println("Insurance Management System closed. Goodbye!");
    }
}
