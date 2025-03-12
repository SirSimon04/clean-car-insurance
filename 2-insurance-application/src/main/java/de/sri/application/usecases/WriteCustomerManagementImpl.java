package de.sri.application.usecases;

import de.sri.domain.entities.*;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.WriteCustomerManagement;
import de.sri.domain.usecases.ReadCustomerManagement;
import de.sri.domain.exceptions.CustomerNotFoundException;

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
    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        if (!customerRepository.findById(customer.getId()).isPresent()) {
            throw new CustomerNotFoundException(customer.getId());
        }
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws CustomerNotFoundException {
        customerRepository.delete(customerId);
    }

    @Override
    public void createAccidentForCustomer(int customerId, Accident accident) throws CustomerNotFoundException {
        Customer customer = this.readCustomerManagement.getCustomer(customerId);
        customer.addAccident(accident);
        updateCustomer(customer);
    }

}
