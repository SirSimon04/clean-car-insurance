package de.sri.application.usecases;

import de.sri.domain.entities.*;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.WriteCustomerManagement;
import de.sri.domain.usecases.ReadCustomerManagement;

public class WriteCustomerManagementImpl implements WriteCustomerManagement {
    private final CustomerRepository customerRepository;
    private final ReadCustomerManagement readCustomerManagement;

    public WriteCustomerManagementImpl(CustomerRepository customerRepository,
            ReadCustomerManagement readCustomerManagement) {
        this.customerRepository = customerRepository;
        this.readCustomerManagement = readCustomerManagement;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    public void createAccidentForCustomer(int customerId, Accident accident) {
        Customer customer = this.readCustomerManagement.getCustomer(customerId);
        customer.addAccident(accident);
        updateCustomer(customer);
    }

}
