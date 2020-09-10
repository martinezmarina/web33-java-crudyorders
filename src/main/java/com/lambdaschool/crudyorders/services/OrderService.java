package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderService {
    Order findOrderById(long id);
    Order save(Order order);
    List<Order> getadvanceamount();
    void delete(long id);
}
