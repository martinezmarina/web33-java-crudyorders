package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository orderrepos;

    @Autowired
    private CustomersRepository custrepos;

    @Autowired
    private PaymentsRepository paymentrepos;

    @Override
    public Order findOrderById(long id) {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }
    @Override
    public List<Order> getadvanceamount() {
        List<Order> rtnList = new ArrayList<>();
        orderrepos.getByAdvanceamountGreaterThanQuery().iterator().forEachRemaining(rtnList::add);

        return rtnList;
    }
    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        if(order.getOrdnum() != 0){
            orderrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));
            newOrder.setOrdnum(order.getOrdnum());
        }
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomer(order.getCustomer());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + "Not Found"));
            newOrder.getPayments().add(newPayment);
        }
        Customer newCustomer = custrepos.findById(newOrder.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + newOrder.getCustomer().getCustcode() + " Not Found"));
        newOrder.setCustomer(newCustomer);

        return orderrepos.save(newOrder);
    }
    @Transactional
    @Override
    public void delete(long id) {
        if(orderrepos.findById(id).isPresent()){
            orderrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }
}
