package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Order, Long> {
    @Query(value = "SELECT o from Order o WHERE o.advanceamount > 0.00")
    List<Order> getByAdvanceamountGreaterThanQuery();
}
