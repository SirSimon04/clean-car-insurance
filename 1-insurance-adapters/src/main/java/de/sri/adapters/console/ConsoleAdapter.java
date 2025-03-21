package de.sri.adapters.console;

import de.sri.domain.usecases.ReadCustomerManagement;
import de.sri.domain.usecases.WriteCustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.EmailAddress;
import de.sri.domain.valueobjects.PersonName;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Ticket;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.exceptions.BaseDomainException;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.directors.CustomerDirector;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleAdapter {
    protected final ReadCustomerManagement readCustomerManagement;
    protected final WriteCustomerManagement writeCustomerManagement;
    protected final PolicyManagement policyManagement;
    protected final TicketManagement ticketManagement;
    protected final Scanner scanner;
    protected final DateTimeFormatter dateFormatter;

    public ConsoleAdapter(ReadCustomerManagement readCustomerManagement,
            WriteCustomerManagement writeCustomerManagement, PolicyManagement policyManagement,
            TicketManagement ticketManagement) {
        this.readCustomerManagement = readCustomerManagement;
        this.writeCustomerManagement = writeCustomerManagement;
        this.policyManagement = policyManagement;
        this.ticketManagement = ticketManagement;
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                printMainMenu();
                int choice = getIntInput("Choose an option: ");
                running = handleChoice(choice);
            } catch (BaseDomainException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                // In einem realen Beispiel sollte man hier loggen.
                System.out.println("An unexpected error occurred. Try again or contact support.");
            }
        }
    }

    private boolean handleChoice(int choice) throws BaseDomainException {
        switch (choice) {
            case 1:
                createCustomer();
                break;
            case 2:
                getCustomer();
                break;
            case 3:
                listAllCustomers();
                break;
            case 4:
                updateCustomer();
                break;
            case 5:
                deleteCustomer();
                break;
            case 6:
                addPolicyToCustomer();
                break;
            case 7:
                createAccidentForCustomer();
                break;
            case 8:
                createTicketForCustomer();
                break;
            case 9:
                getCustomersByPolicyStatus();
                break;
            case 10:
                getCustomersByAccidentCostGreaterThan();
                break;
            case 11:
                getCustomersByTicketSpeedExcessGreaterThan();
                break;
            case 12:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    protected void printMainMenu() {
        System.out.println("\n--- Insurance Management System ---");
        System.out.println("1. Create Customer");
        System.out.println("2. Get Customer");
        System.out.println("3. List All Customers");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println("6. Add Policy to Customer");
        System.out.println("7. Create Accident for Customer");
        System.out.println("8. Create Ticket for Customer");
        System.out.println("9. Get Customers by Policy Status");
        System.out.println("10. Get Customers by Accident Cost Greater Than");
        System.out.println("11. Get Customers by Ticket Speed Excess Greater Than");
        System.out.println("12. Exit");
    }

    protected void createCustomer() throws BaseDomainException {
        System.out.println("\n--- Create New Customer ---");
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        LocalDate dateOfBirth = getDateInput("Enter date of birth (YYYY-MM-DD): ");
        String email = getStringInput("Enter email: ");

        System.out.println("Enter address details:");
        String street = getStringInput("Street: ");
        String city = getStringInput("City: ");
        String state = getStringInput("State: ");
        String zipCode = getStringInput("Zip Code: ");
        String country = getStringInput("Country: ");

        PersonName name = new PersonName(firstName, lastName);
        EmailAddress emailAddr = new EmailAddress(email);
        Address address = new Address(street, city, state, zipCode, country);
        Customer customer = new CustomerDirector(new Customer.Builder()).buildNew(0, name, dateOfBirth, emailAddr,
                address);

        customer = writeCustomerManagement.createCustomer(customer);
        System.out.println("Customer created successfully with ID: " + customer.getId());
    }

    protected void getCustomer() throws CustomerNotFoundException {
        int id = getIntInput("Enter customer ID: ");
        Customer customer = readCustomerManagement.getCustomer(id);
        printCustomerDetails(customer);
    }

    protected void listAllCustomers() {
        System.out.println("\n--- All Customers ---");
        readCustomerManagement.getAllCustomers().forEach(this::printCustomerDetails);
    }

    protected void updateCustomer() throws BaseDomainException {
        int id = getIntInput("Enter customer ID to update: ");
        Customer customer = readCustomerManagement.getCustomer(id);

        System.out.println("\n--- Update Customer ---");
        System.out.println("Press Enter to keep the current value.");

        String firstName = getStringInputWithDefault("Enter new first name", customer.getName().getFirstName());
        String lastName = getStringInputWithDefault("Enter new last name", customer.getName().getLastName());
        LocalDate dateOfBirth = getDateInputWithDefault("Enter new date of birth (YYYY-MM-DD)",
                customer.getDateOfBirth());
        String email = getStringInputWithDefault("Enter new email", customer.getEmail().getEmailAddress());

        Address currentAddress = customer.getAddress();
        String street = getStringInputWithDefault("Enter new street", currentAddress.getStreet());
        String city = getStringInputWithDefault("Enter new city", currentAddress.getCity());
        String state = getStringInputWithDefault("Enter new state", currentAddress.getState());
        String zipCode = getStringInputWithDefault("Enter new zip code", currentAddress.getZipCode());
        String country = getStringInputWithDefault("Enter new country", currentAddress.getCountry());

        PersonName name = new PersonName(firstName, lastName);
        EmailAddress emailAddr = new EmailAddress(email);
        Address newAddress = new Address(street, city, state, zipCode, country);
        Customer updatedCustomer = new CustomerDirector(new Customer.Builder()).buildNew(id, name, dateOfBirth,
                emailAddr, newAddress);

        writeCustomerManagement.updateCustomer(updatedCustomer);
        System.out.println("Customer updated successfully.");
    }

    protected void deleteCustomer() throws CustomerNotFoundException {
        int id = getIntInput("Enter customer ID to delete: ");
        writeCustomerManagement.deleteCustomer(id);
        System.out.println("Customer deleted successfully.");
    }

    protected void addPolicyToCustomer() throws BaseDomainException {
        int customerId = getIntInput("Enter customer ID: ");
        System.out.println("\n--- Add New Policy ---");
        String program = getStringInput("Enter policy program (BASIC/STANDARD/DELUXE): ");
        double carValue = getDoubleInput("Enter car value: ");

        Policy policy = new Policy(0, PolicyStatus.ACTIVE, PolicyProgram.valueOf(program.toUpperCase()), carValue);

        policyManagement.addPolicyToCustomer(customerId, policy);
        System.out.println("Policy added successfully to customer.");
    }

    protected void createAccidentForCustomer() throws BaseDomainException {
        int customerId = getIntInput("Enter customer ID: ");
        System.out.println("\n--- Create New Accident ---");
        double cost = getDoubleInput("Enter accident cost: ");
        LocalDate date = getDateInput("Enter accident date (YYYY-MM-DD): ");
        int policyId = getIntInput("Enter policy ID: ");

        Accident accident = new Accident(0, cost, date, policyId);
        writeCustomerManagement.createAccidentForCustomer(customerId, accident);
        System.out.println("Accident created successfully for customer.");
    }

    protected void createTicketForCustomer() throws BaseDomainException {
        int customerId = getIntInput("Enter customer ID: ");
        System.out.println("\n--- Create New Ticket ---");
        LocalDate date = getDateInput("Enter ticket date (YYYY-MM-DD): ");
        double speedExcess = getDoubleInput("Enter speed excess: ");

        Ticket ticket = new Ticket(0, date, speedExcess);
        ticketManagement.createTicketForCustomer(customerId, ticket);
        System.out.println("Ticket created successfully for customer.");
    }

    protected void getCustomersByPolicyStatus() {
        String statusInput = getStringInput("Enter policy status (ACTIVE/INACTIVE): ");
        PolicyStatus status = PolicyStatus.valueOf(statusInput.toUpperCase());
        List<Customer> customers = readCustomerManagement.getCustomersByPolicyStatus(status);

        System.out.println("\n--- Customers with Policy Status: " + status + " ---");
        customers.forEach(this::printCustomerDetails);
    }

    protected void getCustomersByAccidentCostGreaterThan() {
        double cost = getDoubleInput("Enter accident cost threshold: ");
        List<Customer> customers = readCustomerManagement.getCustomersByAccidentCostGreaterThan(cost);

        System.out.println("\n--- Customers with Accident Cost Greater Than: " + cost + " ---");
        customers.forEach(this::printCustomerDetails);
    }

    protected void getCustomersByTicketSpeedExcessGreaterThan() {
        double speedExcess = getDoubleInput("Enter speed excess threshold: ");
        List<Customer> customers = readCustomerManagement.getCustomersByTicketSpeedExcessGreaterThan(speedExcess);

        System.out.println("\n--- Customers with Ticket Speed Excess Greater Than: " + speedExcess + " ---");
        customers.forEach(this::printCustomerDetails);
    }

    protected void printCustomerDetails(Customer customer) {
        System.out.println("\nCustomer Details:");
        System.out.println("ID: " + customer.getId());
        System.out.println("Name: " + customer.getName().getFirstName() + " " + customer.getName().getLastName());
        System.out.println("Date of Birth: " + customer.getDateOfBirth());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Address: " + customer.getAddress());

        System.out.println("Policies:");
        customer.getPolicies().forEach(System.out::println);

        System.out.println("Accidents:");
        customer.getAccidents().forEach(System.out::println);

        System.out.println("Tickets:");
        customer.getTickets().forEach(System.out::println);
    }

    protected String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    protected String getStringInputWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    protected int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    protected double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    protected LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    protected LocalDate getDateInputWithDefault(String prompt, LocalDate defaultValue) {
        while (true) {
            try {
                System.out.print(prompt + " [" + defaultValue.format(dateFormatter) + "]: ");
                String input = scanner.nextLine().trim();
                return input.isEmpty() ? defaultValue : LocalDate.parse(input, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
}
