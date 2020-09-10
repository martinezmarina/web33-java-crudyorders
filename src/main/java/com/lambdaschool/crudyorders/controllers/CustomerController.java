package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerService;
import com.lambdaschool.crudyorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomerOrders() {
        List<Customer> customersOrders = customerService.listAllCustomerOrders();
        return new ResponseEntity<>(customersOrders, HttpStatus.OK);
    }
    // http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{id}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerById(@PathVariable long id) {
        Customer c = customerService.findCustomerById(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
    // http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{name}", produces = {"application/json"})
    public ResponseEntity<?> listCustomersByLikeName(@PathVariable String name) {
        List<Customer> customers = customerService.listCustomersByLikeName(name);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    //http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?>getOrderCount(){
        List<OrderCount> orderCount = customerService.getOrderCount();
        return new ResponseEntity<>(orderCount, HttpStatus.OK);
    }
    // POST /customers/customer
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newCustomer.getCustcode())
                .build()
                .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    //  PUT /customers/customer/{custcode}
    @PutMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer,
                                                @PathVariable long id){
        updateCustomer.setCustcode(id);
        customerService.save(updateCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //PATCH /customers/customer/{custcode}
    @PatchMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer,
                                            @PathVariable long id){
        customerService.update(updateCustomer, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //DELETE /customers/customer/{custcode}
    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
