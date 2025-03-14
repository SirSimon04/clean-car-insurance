package de.sri.adapters.console;

import de.sri.domain.usecases.ReadCustomerManagement;
import de.sri.domain.usecases.WriteCustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Ticket;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.directors.CustomerDirector;
import de.sri.domain.directors.TestCustomerDirector;
import de.sri.domain.exceptions.CarTooExpensiveException;
import de.sri.domain.exceptions.CustomerTooYoungException;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleAdapterTest {

    @Mock
    private ReadCustomerManagement readCustomerManagement;

    @Mock
    private WriteCustomerManagement writeCustomerManagement;

    @Mock
    private PolicyManagement policyManagement;

    @Mock
    private TicketManagement ticketManagement;

    @InjectMocks
    private ConsoleAdapter consoleAdapter;

    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    private String getOutput() {
        return outputStream.toString();
    }

    @Test
    void createCustomer_success() throws PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn("John").when(consoleAdapter).getStringInput(eq("Enter first name: "));
        doReturn("Doe").when(consoleAdapter).getStringInput(eq("Enter last name: "));
        doReturn(LocalDate.of(2000, 1, 1)).when(consoleAdapter).getDateInput(eq("Enter date of birth (YYYY-MM-DD): "));
        doReturn("john.doe@example.com").when(consoleAdapter).getStringInput(eq("Enter email: "));
        doReturn("Street").when(consoleAdapter).getStringInput(eq("Street: "));
        doReturn("City").when(consoleAdapter).getStringInput(eq("City: "));
        doReturn("State").when(consoleAdapter).getStringInput(eq("State: "));
        doReturn("12345").when(consoleAdapter).getStringInput(eq("Zip Code: "));
        doReturn("Country").when(consoleAdapter).getStringInput(eq("Country: "));

        Customer createdCustomer = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        when(writeCustomerManagement.createCustomer(any(Customer.class))).thenReturn(createdCustomer);

        consoleAdapter.start();

        String output = getOutput();

        assertTrue(output.contains("Customer created successfully with ID: 1"));
        verify(writeCustomerManagement, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void getCustomer_success() throws CustomerNotFoundException, PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(2).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());

        Customer customer = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        when(readCustomerManagement.getCustomer(1)).thenReturn(customer);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("Customer Details:"));
        assertTrue(output.contains("ID: 1"));
        verify(readCustomerManagement, times(1)).getCustomer(1);
    }

    @Test
    void listAllCustomers_success() throws PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(3).doReturn(12).when(consoleAdapter).getIntInput(anyString());

        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        Customer customer2 = new CustomerDirector(new Customer.Builder()).buildNew(2, "Jane", "Smith",
                LocalDate.of(1995, 5, 15), "jane.smith@example.com",
                new Address("Another Street", "Another City", "Another State", "54321", "Another Country"));
        customers.add(customer1);
        customers.add(customer2);

        when(readCustomerManagement.getAllCustomers()).thenReturn(customers);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("--- All Customers ---"));
        assertTrue(output.contains("ID: 1"));
        assertTrue(output.contains("ID: 2"));
        verify(readCustomerManagement, times(1)).getAllCustomers();
    }

    @Test
    void updateCustomer_success() throws CustomerNotFoundException, PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);
        doReturn(4).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn("Jane").when(consoleAdapter).getStringInputWithDefault(eq("Enter new first name"), eq("John"));
        doReturn("Smith").when(consoleAdapter).getStringInputWithDefault(eq("Enter new last name"), eq("Doe"));
        doReturn(LocalDate.of(2001, 1, 1)).when(consoleAdapter)
                .getDateInputWithDefault(eq("Enter new date of birth (YYYY-MM-DD)"), any(LocalDate.class));
        doReturn("jane.smith@example.com").when(consoleAdapter).getStringInputWithDefault(eq("Enter new email"),
                eq("john.doe@example.com"));
        doReturn("NewStreet").when(consoleAdapter).getStringInputWithDefault(eq("Enter new street"), eq("Street"));
        doReturn("NewCity").when(consoleAdapter).getStringInputWithDefault(eq("Enter new city"), eq("City"));
        doReturn("NewState").when(consoleAdapter).getStringInputWithDefault(eq("Enter new state"), eq("State"));
        doReturn("54321").when(consoleAdapter).getStringInputWithDefault(eq("Enter new zip code"), eq("12345"));
        doReturn("NewCountry").when(consoleAdapter).getStringInputWithDefault(eq("Enter new country"), eq("Country"));

        Customer existingCustomer = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        Customer updatedCustomer = new CustomerDirector(new Customer.Builder()).buildNew(1, "Jane", "Smith",
                LocalDate.of(2001, 1, 1), "jane.smith@example.com",
                new Address("NewStreet", "NewCity", "NewState", "54321", "NewCountry"));

        when(readCustomerManagement.getCustomer(1)).thenReturn(existingCustomer);

        consoleAdapter.start();

        verify(writeCustomerManagement, times(1)).updateCustomer(updatedCustomer);
        verify(readCustomerManagement, times(1)).getCustomer(1);
    }

    @Test
    void deleteCustomer_success() throws CustomerNotFoundException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(5).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());

        doNothing().when(writeCustomerManagement).deleteCustomer(1);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("Customer deleted successfully."));
        verify(writeCustomerManagement, times(1)).deleteCustomer(1);
    }

    @Test
    void addPolicyToCustomer_success() throws CustomerNotFoundException, CarTooExpensiveException,
            CustomerTooYoungException, InvalidPremiumAmountException, PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(6).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn("BASIC").when(consoleAdapter).getStringInput(eq("Enter policy program (BASIC/STANDARD/DELUXE): "));
        doReturn(10000.0).when(consoleAdapter).getDoubleInput(eq("Enter car value: "));

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("Policy added successfully to customer."));
        verify(policyManagement, times(1)).addPolicyToCustomer(eq(1), any(Policy.class));
    }

    @Test
    void createAccidentForCustomer_success() throws CustomerNotFoundException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(7).doReturn(1).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn(1000.0).when(consoleAdapter).getDoubleInput(eq("Enter accident cost: "));
        doReturn(LocalDate.of(2023, 1, 1)).when(consoleAdapter).getDateInput(eq("Enter accident date (YYYY-MM-DD): "));

        doNothing().when(writeCustomerManagement).createAccidentForCustomer(eq(1), any(Accident.class));

        consoleAdapter.start();

        verify(writeCustomerManagement, times(1)).createAccidentForCustomer(eq(1), any(Accident.class));
    }

    @Test
    void createTicketForCustomer_success()
            throws CustomerNotFoundException, InvalidPremiumAmountException, PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(8).doReturn(1).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn(LocalDate.of(2023, 1, 1)).when(consoleAdapter).getDateInput(eq("Enter ticket date (YYYY-MM-DD): "));
        doReturn(50.0).when(consoleAdapter).getDoubleInput(eq("Enter speed excess: "));

        doNothing().when(ticketManagement).createTicketForCustomer(eq(1), any(Ticket.class));

        consoleAdapter.start();

        verify(ticketManagement, times(1)).createTicketForCustomer(eq(1), any(Ticket.class));
    }

    @Test
    void getCustomersByPolicyStatus_success() throws PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(9).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn("ACTIVE").when(consoleAdapter).getStringInput(eq("Enter policy status (ACTIVE/INACTIVE): "));

        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        Customer customer2 = new CustomerDirector(new Customer.Builder()).buildNew(2, "Jane", "Smith",
                LocalDate.of(1995, 5, 15), "jane.smith@example.com",
                new Address("Another Street", "Another City", "Another State", "54321", "Another Country"));
        customers.add(customer1);
        customers.add(customer2);

        when(readCustomerManagement.getCustomersByPolicyStatus(PolicyStatus.ACTIVE)).thenReturn(customers);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("--- Customers with Policy Status: ACTIVE ---"));
        assertTrue(output.contains("ID: 1"));
        assertTrue(output.contains("ID: 2"));
        verify(readCustomerManagement, times(1)).getCustomersByPolicyStatus(PolicyStatus.ACTIVE);
    }

    @Test
    void getCustomersByAccidentCostGreaterThan_success() throws PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(10).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn(1000.0).when(consoleAdapter).getDoubleInput(eq("Enter accident cost threshold: "));

        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        Customer customer2 = new CustomerDirector(new Customer.Builder()).buildNew(2, "Jane", "Smith",
                LocalDate.of(1995, 5, 15), "jane.smith@example.com",
                new Address("Another Street", "Another City", "Another State", "54321", "Another Country"));
        customers.add(customer1);
        customers.add(customer2);

        when(readCustomerManagement.getCustomersByAccidentCostGreaterThan(1000.0)).thenReturn(customers);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("--- Customers with Accident Cost Greater Than: 1000.0 ---"));
        assertTrue(output.contains("ID: 1"));
        assertTrue(output.contains("ID: 2"));
        verify(readCustomerManagement, times(1)).getCustomersByAccidentCostGreaterThan(1000.0);
    }

    @Test
    void getCustomersByTicketSpeedExcessGreaterThan_success() throws PropertyNotNullException {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(11).doReturn(12).when(consoleAdapter).getIntInput(anyString());
        doReturn(50.0).when(consoleAdapter).getDoubleInput(eq("Enter speed excess threshold: "));

        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new CustomerDirector(new Customer.Builder()).buildNew(1, "John", "Doe",
                LocalDate.of(2000, 1, 1), "john.doe@example.com",
                new Address("Street", "City", "State", "12345", "Country"));
        Customer customer2 = new CustomerDirector(new Customer.Builder()).buildNew(2, "Jane", "Smith",
                LocalDate.of(1995, 5, 15), "jane.smith@example.com",
                new Address("Another Street", "Another City", "Another State", "54321", "Another Country"));
        customers.add(customer1);
        customers.add(customer2);

        when(readCustomerManagement.getCustomersByTicketSpeedExcessGreaterThan(50.0)).thenReturn(customers);

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("--- Customers with Ticket Speed Excess Greater Than: 50.0 ---"));
        assertTrue(output.contains("ID: 1"));
        assertTrue(output.contains("ID: 2"));
        verify(readCustomerManagement, times(1)).getCustomersByTicketSpeedExcessGreaterThan(50.0);
    }

    @Test
    void exit_success() {
        consoleAdapter = Mockito.spy(consoleAdapter);

        doReturn(12).when(consoleAdapter).getIntInput(anyString());

        consoleAdapter.start();

        String output = getOutput();
        System.out.println("Actual Output:\n" + output);

        assertTrue(output.contains("--- Insurance Management System ---")); // check for menu output
    }
}
