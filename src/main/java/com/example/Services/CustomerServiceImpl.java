package com.example.Services;

import com.example.DAOs.CustomerDAO;
import com.example.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
//  No need to write explicit
//  session.getTransaction().beginTransaction();
//  session.getTransaction().commit();
    public List<Customer> getCustomers() {
        List<Customer> list = customerDAO.getCustomers();
        System.out.println("Customers list from Services::   " + list);
        return list;
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public Customer getCustomerByID(int customerId) {
        Customer customer = customerDAO.getCustomerByID(customerId);
        System.out.println("Customer by ID from Services::   " + customer);
        return customer;
    }

    @Override
    @Transactional
    public void deleteCustomer(int customerId) {
        customerDAO.deleteCustomer(customerId);
    }

    @Override
    @Transactional
    public List<Customer> getCustomerByName(String searchName) {
        List <Customer> customer = customerDAO.getCustomerByName(searchName);
        return customer;
    }
}
