package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCount;

import java.util.List;

public interface CustomerService {
    List<Customer> listAllCustomerOrders();
    Customer findCustomerById(long id);
    List<Customer> listCustomersByLikeName(String name);
    List<OrderCount> getOrderCount();
    Customer save(Customer customer);
    Customer update(Customer customer, long id);
    void delete(long id);
}
