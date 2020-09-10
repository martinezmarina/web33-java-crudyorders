package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service (value = "paymentServices")
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentsRepository payrepos;

    @Override
    public Payment save(Payment payment) {
        return payrepos.save(payment);
    }
}
