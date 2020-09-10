package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomersRepository custrepos;
    @Autowired
    private OrdersRepository ordersrepos;
    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public List<Customer> listAllCustomerOrders() {
        List<Customer> rtnList = new ArrayList<>();

        custrepos.findAll()
                .iterator()
                .forEachRemaining(rtnList::add);

        return rtnList;
    }

    @Override
    public Customer findCustomerById(long id) {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> listCustomersByLikeName(String name) {
        List<Customer> customerList = new ArrayList<>();
        custrepos.findByCustnameContainingIgnoringCase(name).iterator().forEachRemaining(customerList::add);
        return customerList;
    }

    @Override
    public List<OrderCount> getOrderCount() {
        return custrepos.getOrderCount();
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        newCustomer.getOrders().clear();

        for (Order o : customer.getOrders()) {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(newCustomer);
            newOrder.setOrderdescription(o.getOrderdescription());
            newCustomer.getOrders().add(newOrder);
        }
        Agent newAgent = agentrepos.findById(newCustomer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + newCustomer.getAgent().getAgentcode() + " Not Found"));
        newCustomer.setAgent(newAgent);

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {
        Customer currentCustomer = custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
        if (customer.getCustcity() != null) {
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getCustcountry() != null) {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getCustname() != null) {
            currentCustomer.setCustname(customer.getCustname());
        }
        if (customer.getGrade() != null) {
            currentCustomer.setGrade(customer.getGrade());
        }
        if (customer.hasvalueforopeningamt) {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforoutstandingamt) {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.hasvalueforpaymentamt) {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.getPhone() != null) {
            currentCustomer.setPhone(customer.getPhone());
        }
        if (customer.hasvalueforreceiveamt) {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if (customer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getOrders().size() > 0) {
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setCustomer(currentCustomer);
                newOrder.setOrderdescription(o.getOrderdescription());
                currentCustomer.getOrders().add(newOrder);
            }
        }
        if (customer.getAgent() != null) {
            Agent newAgent = agentrepos.findById(customer.getAgent().getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " Not Found"));

            currentCustomer.setAgent(newAgent);
        }
            return custrepos.save(currentCustomer);
    }
        @Transactional
        @Override
        public void delete(long id){
            if (custrepos.findById(id).isPresent()) {
                custrepos.deleteById(id);
            } else {
                throw new EntityNotFoundException("Restaurant " + id + " Not Found");
            }
        }
    }