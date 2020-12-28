package com.example.DAOs;

import com.example.Model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //For component scanning and SQL exceptions
public class CustomerDAOImpl implements CustomerDAO {

    //Inject SessionFactory bean
    @Autowired
    private SessionFactory sessionFactory;  //bean id sessionFactory
//The annotation substitute this:
//    private final SessionFactory sessionFactory;
//
//    public CustomerDAOImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public List<Customer> getCustomers() {
//        try (Session session = sessionFactory.getCurrentSession()) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> customersQuery = session.createQuery("from Customer order by lastName", Customer.class);
                                                                //from can be removed?
        List<Customer> customers = customersQuery.getResultList();
        System.out.println("Customers list from DAO:   " + customers);
//        }
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
        System.out.println("   Customer " + customer + " saved");
    }

    @Override
    public Customer getCustomerByID(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerId);
        System.out.println("Customer by ID from DAO" + customer);
        return customer;
    }

    @Override
    public void deleteCustomer(int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerId);
        session.delete(customer);
        System.out.println("Customer " + customer + " was deleted");

//        Query query = session.createQuery("delete from Customer where id=:customerID");
//        query.setParameter("customerID", customerId);
//        query.executeUpdate();  //For any of read, delete, update
//        System.out.println("Customer deleted");
    }

    @Override
    public List<Customer> getCustomerByName(String searchName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> query = null;
        if (searchName != null && searchName.trim().length() > 0) {
            query = session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
        } else {
            query = session.createQuery("from Customer", Customer.class);
        }
        List<Customer> customer = query.getResultList();
        System.out.println("Customer searched by name is: " + customer);
        return customer;
    }
}
