package com.example.Controllers;

import com.example.Exceptions.CustomException;
import com.example.Model.Customer;
import com.example.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {

        return customerService.getCustomers();
    }

    @GetMapping("/customers/{customerID}")
    public Customer getCustomersById(@PathVariable String customerID) {
        Customer customer = customerService.getCustomerByID(Integer.parseInt(customerID));
        if (customer == null) {
            throw new CustomException("Customer with ID: " + customerID + " not found.");
        }
        return customer;
    }

    @GetMapping("/customersByName/{customerName}")
    public List<Customer> getCustomersByName(@PathVariable String customerName) {

        List<Customer> customerList = customerService.getCustomerByName(customerName);

        if (customerList == null || customerList.size() == 0) {
            throw new CustomException("Customer named " + customerName + " not found.");
        }

        return customerList;
    }

    @PostMapping("/customers")
    public Customer postCustomer(@RequestBody Customer theCustomer) {
        theCustomer.setId(0);
        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer) {
        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    @DeleteMapping("/customers/{customerID}")
    public String deleteCustomersById(@PathVariable String customerID) {
        Customer customer = customerService.getCustomerByID(Integer.parseInt(customerID));
        if (customer == null) {
            throw new CustomException("Customer with ID: " + customerID + " not found.");
        }

        customerService.deleteCustomer(Integer.parseInt(customerID));
        return "Customer with id "+ customer.getId()+" was deleted." ;
    }

}
