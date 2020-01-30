package com.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PaymentOption {

    @Id
    private String id;

    private PaymentType type;

    private int fee;

    public PaymentOption(PaymentType type, int fee) {
        this.type = type;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public PaymentType getType() {
        return type;
    }

}
