package com.example.DAOs;

import com.example.Model.Customer;

import java.util.List;

public interface CustomerDAO {
    public List<Customer> getCustomers();

    public void saveCustomer(Customer customer);

    public Customer getCustomerByID(int customerId);

    public void deleteCustomer(int customerId);

    public List <Customer> getCustomerByName(String searchName);
}
